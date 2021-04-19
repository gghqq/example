package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName SemaphoreSycDemo
 * @Description :  　单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 *  Semaphore就是一个信号量，它的作用是限制某段代码块的并发数。
 *  Semaphore有一个构造函数，可以传入一个int型整数n，表示某段代码最多只有n个线程可以访问，如果超出了n，那么请等待，等到某个线程执行完毕这段代码块，下一个线程再进入。
 *  由此可以看出如果Semaphore构造函数中传入的int型整数n=1，相当于变成了一个synchronized了。
 *
 * @Author agan
 * @Date 2021/4/19 14:05
 **/

public class SemaphoreSycDemo {

    public static void main(String[] args) {
        final Business business = new Business();
        ExecutorService executor =  Executors.newFixedThreadPool(3);
        for(int i=0;i<10;i++)
        {
            executor.execute(
                    () -> business.service()

            );
        }
        executor.shutdown();
    }

    private static class Business
    {
        private int count;
        Lock lock = new ReentrantLock();
        Semaphore sp = new Semaphore(1);
        public void service()
        {
//            lock.lock();
            try {
                sp.acquire(); //当前线程使用count变量的时候将其锁住，不允许其他线程访问
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                count++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(count);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            finally
            {
//                lock.unlock();
                sp.release();  //释放锁
            }
        }
    }


}
