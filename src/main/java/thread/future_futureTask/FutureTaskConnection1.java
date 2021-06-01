package thread.future_futureTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: 错误示例
 * @description: 在很多高并发的环境下，往往我们只需要某些任务只执行一次。
 * 这种使用情景FutureTask的特性恰能胜任。举一个例子，假设有一个带key的连接池，
 * 当key存在时，即直接返回key对应的对象；当key不存在时，则创建连接。对于这样的应用场景，
 * 通常采用的方法为使用一个Map对象来存储key和连接池对应的对应关系，典型的代码如下
 * 在例子中，我们通过加锁确保高并发环境下的线程安全，也确保了connection只创建一次，然而却牺牲了性能。
 */
public class FutureTaskConnection1 {
    private static Map<String, Connection> connectionPool = new HashMap<>();
    private static ReentrantLock lock = new ReentrantLock();

    public static Connection getConnection(String key) {
        try {
            lock.lock();
            Connection connection = connectionPool.get(key);
            if (connection == null) {
                Connection newConnection = createConnection();
                connectionPool.put(key, newConnection);
                return newConnection;
            }
            return connection;
        } finally {
            lock.unlock();
        }
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}




/**
 * @description: 改用ConcurrentHash的情况下，几乎可以避免加锁的操作，性能大大提高。
 * <p>
 * 但是在高并发的情况下有可能出现Connection被创建多次的现象。
 * 为什么呢？因为创建Connection是一个耗时操作，假设多个线程涌入getConnection方法，都发现key对应的键不存在，
 * 于是所有涌入的线程都开始执行conn=createConnection()，只不过最终只有一个线程能将connection插入到map里。
 * 但是这样以来，其它线程创建的的connection就没啥价值，浪费系统开销。
 *
 * 这时最需要解决的问题就是当key不存在时，创建Connection的动作（conn=createConnection();）
 * 能放在connectionPool.putIfAbsent()之后执行，这正是FutureTask发挥作用的时机

 */
 class FutureTaskConnection2 {
    private static ConcurrentHashMap<String, Connection> connectionPool = new ConcurrentHashMap<>();

    public static Connection getConnection(String key) {
        Connection connection = connectionPool.get(key); //当多个线程进来时
        if (connection == null) { //同时判空
            connection = createConnection();  //  因为创建连接比较耗时   重复创建
            // 只能判断前面是否有线程已经创建   根据putIfAbsent的返回值判断是否有线程抢先插入了
            Connection returnConnection = connectionPool.putIfAbsent(key, connection);
            if (returnConnection != null) {
                connection = returnConnection; //如果不为null就使用已经创建的
            }
        } else {
            return connection;
        }
        return connection;
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}


/**
 * @description: FutureTask在高并发环境下确保任务只执行一次
 * 这时最需要解决的问题就是当key不存在时，创建Connection的动作（conn=createConnection();）
 * 能放在connectionPool.putIfAbsent()之后执行，这正是FutureTask发挥作用的时机，
 * 基于ConcurrentHashMap和FutureTask的改造代码如下：
 */
 class FutureTaskConnection3 {
    private static ConcurrentHashMap<String, FutureTask<Connection>> connectionPool = new ConcurrentHashMap<>();

    public static Connection getConnection(String key) {
        FutureTask<Connection> connectionFutureTask = connectionPool.get(key); //获取FutureTask返回的连接
        try {
            if (connectionFutureTask != null) {
                return connectionFutureTask.get();  //如果已经有就直接获取
            } else {
//                Callable<Connection> callable = () -> createConnection();
                Callable<Connection> callable = new Callable<Connection>() {  //创建一个线程 任务是创建连接
                    @Override
                    public Connection call() throws Exception {
                        return createConnection();
                    }
                };
                FutureTask<Connection> newTask = new FutureTask<>(callable);
//                putIfAbsent   如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
//                ..putIfAbsent只会有一个线程放入值返回null ,其他线程会跳过判断直接等待获取连接...
                FutureTask<Connection> returnFt = connectionPool.putIfAbsent(key, newTask);
                if (returnFt == null) {
                    connectionFutureTask = newTask;
                    newTask.run();
                }
                return connectionFutureTask.get();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}