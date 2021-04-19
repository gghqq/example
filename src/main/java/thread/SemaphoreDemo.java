package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName Semaphore
 * @Description: Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
 * Semaphore的主要方法摘要：
 * 　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
 * 　　void release():释放一个许可，将其返回给信号量。
 * 　　int availablePermits():返回此信号量中当前可用的许可数。
 * 　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
 * @Author agan
 * @Date 2021/4/19 13:45
 **/

public class SemaphoreDemo {
    //    构造函数有公平和非公平,默认非公平
    private static Semaphore semaphore = new Semaphore(3, false);//创建Semaphore信号量，初始化许可大小为3

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {

            Thread.sleep(1000L);

            Runnable a = new Thread(() -> {

                try {
                    semaphore.acquire();//请求获得许可，如果有可获得的许可则继续往下执行，许可数减1。否则进入阻塞状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程" + Thread.currentThread().getName() +
                        "进入，当前已有" + (3 - semaphore.availablePermits()) + "个并发");

                try {
                    Thread.sleep((long) (Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程" + Thread.currentThread().getName() +
                        "即将离开");
//              semaphore.release(1);
                semaphore.release();//释放许可，许可数加1
                System.out.println("线程" + Thread.currentThread().getName() +
                        "已离开，当前已有" + (3 - semaphore.availablePermits()) + "个并发");

            });
            pool.submit(a);
        }
    }
}
