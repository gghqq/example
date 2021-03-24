package thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName ThreadPool
 * @Description
 * @Author agan
 * @Date 2021/3/4 16:18
 **/

public class ThreadPool {

    int taskSize = 10;


    public void getResult(){
        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> futures = Arrays.asList();

        for (int i = 0 ; 0 < 10 ; i++){
            Callable c = new MyCallable(i + "");
            // 执行任务并获取 Future 对象
            Future f = pool.submit(c);
            futures.add(f);
        }
        //关闭线程池
//        pool.shutdown();
//        获取所有任务的并发返回结果
//        futures.stream().forEach(future -> {
//            try {
//                System.out.println(future.get().toString());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
    }
}

class MyCallable implements  Callable{


    public MyCallable(String s) {

    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
