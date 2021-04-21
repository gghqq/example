package thread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReadWriteLockDemo https://www.cnblogs.com/xiaoxi/p/9140541.html
 * @Description
 *
 * 在线程持有读锁的情况下，该线程不能取得写锁(因为获取写锁的时候，如果发现当前的读锁被占用，就马上获取失败，不管读锁是不是被当前线程持有)。
 *    在线程持有写锁的情况下，该线程可以继续获取读锁（获取读锁时如果发现写锁被占用，只有写锁没有被当前线程占用的情况才会获取失败）。
 *    仔细想想，这个设计是合理的：因为当线程获取读锁的时候，可能有其他线程同时也在持有读锁，因此不能把获取读锁的线程“升级”为写锁；而对于获得写锁的线程，它一定独占了读写锁，因此可以继续让它获取读锁，当它同时获取了写锁和读锁后，还可以先释放写锁继续持有读锁，这样一个写锁就“降级”为了读锁。
 * 综上：
 * 一个线程要想同时持有写锁和读锁，必须先获取写锁再获取读锁；写锁可以“降级”为读锁；读锁不能“升级”为写锁。
 * @Author agan
 * @Date 2021/4/19 17:36
 **/

public class ReadWriteLockDemo {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final TheData theData = new TheData();
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                theData.get();
            }).start();
        }
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                theData.set(new Random().nextInt(1000));
            }).start();
        }
    }

}

class TheData {
    private Integer data = 0;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void get() {
        rwLock.readLock().lock();
//        rwLock.readLock().tryLock();
        try {
            System.out.println("ready to read: " + Thread.currentThread().getName() +" time   " +  System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " now Data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock(); //使用try finally防止死锁
        }
    }

    public void set(Integer data) {
        rwLock.writeLock().lock();
//        rwLock.readLock().tryLock();
        try {
            System.out.println("write to read: " + Thread.currentThread().getName()+" time   " + System.currentTimeMillis());
            Thread.sleep(1000);
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "  now Data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
