package thread;

import java.security.AccessController;
import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolExecutorDemo   https://www.cnblogs.com/dolphin0520/p/3932921.html
 *
 * @Description  CPU执行时是通过给线程划分时间片来进行多个线程执行,线程从切换时会保存上一个线程的任务状态,切回该线程再重新获取任务状态.这个叫做线程上下文切换
 * 线程池为了降低线程的创建和销毁所需要的资源.
 *
 *　　 Executor是一个顶层接口，在它里面只声明了一个方法execute(Runnable)，返回值为void，参数为Runnable类型，从字面意思可以理解，就是用来执行传进去的任务的；
 * 　 然后ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、invokeAny以及shutDown等；
 * 　 抽象类AbstractExecutorService实现了ExecutorService接口，基本实现了ExecutorService中声明的所有方法；
 * 　 然后ThreadPoolExecutor继承了类AbstractExecutorService。
 *
 * javaDoc中建议使用Executors类中提供的几个静态方法来创建线程池
 *    Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
 *    Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
 *    Executors.newFixedThreadPool(int);    //创建固定容量大小的缓冲池
 *    FixedThreadPool：可重用固定线程数的线程池。（适用于负载比较重的服务器） FixedThreadPool使用无界队列LinkedBlockingQueue作为线程池的工作队列 该线程池中的线程数量始终不变。当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务。
 *    SingleThreadExecutor：只会创建一个线程执行任务。（适用于需要保证顺序执行各个任务；并且在任意时间点，没有多线程活动的场景。） SingleThreadExecutorl也使用无界队列LinkedBlockingQueue作为工作队列 若多余一个任务被提交到该线程池，任务会被保存在一个任务队列中，待线程空闲，按先入先出的顺序执行队列中的任务。
 *    CachedThreadPool：是一个会根据需要调整线程数量的线程池。（大小无界，适用于执行很多的短期异步任务的小程序，或负载较轻的服务器） CachedThreadPool使用没有容量的SynchronousQueue作为线程池的工作队列，但CachedThreadPool的maximumPool是无界的。 线程池的线程数量不确定，但若有空闲线程可以复用，则会优先使用可复用的线程。若所有线程均在工作，又有新的任务提交，则会创建新的线程处理任务。所有线程在当前任务执行完毕后，将返回线程池进行复用。
 *    ScheduledThreadPool：继承自ThreadPoolExecutor。它主要用来在给定的延迟之后运行任务，或者定期执行任务。使用DelayQueue作为任务队列。
 *
 *  如何配置线程池的大小:
 * 　　一般需要根据任务的类型来配置线程池大小：
 * 　　如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 NCPU+1
 * 　　如果是IO密集型任务，参考值可以设置为2*NCPU
 *
 * @Author agan
 * @Date 2021/4/6 17:13
 **/

public class ThreadPoolExecutorDemo {
    static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(10);
    private static ThreadPoolExecutor tpe = new ThreadPoolExecutor(3,10,10, TimeUnit.MINUTES,workQueue);

    /**
     *  所有的构造方法最后都调用该构造方法
     * @param corePoolSize 核心池的大小,在创建了线程池后,默认情况下,线程池中的线程数为0,当有任务来之后,就会创建一个线程去执行任务,当线程池中的线程数目达到corePoolSize后,就会把到达的任务放到缓存队列当中；
     *                     除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法,从这2个方法的名字就可以看出,是预创建线程的意思,即在没有任务到来之前就创建corePoolSize个线程或者一个线程。
     * @param maximumPoolSize 线程池最大线程数,表示在线程池中最多能创建多少个线程；
     * @param keepAliveTime 表示线程没有任务执行时最多保持多久时间会终止。默认情况下,只有当线程池中的线程数大于corePoolSize时,keepAliveTime才会起作用,直到线程池中的线程数不大于corePoolSize,
     *                      即当线程池中的线程数大于corePoolSize时,如果一个线程空闲的时间达到keepAliveTime,则会终止,直到线程池中的线程数不超过corePoolSize。
     *                      但是如果调用了allowCoreThreadTimeOut(boolean)方法,在线程池中的线程数不大于corePoolSize时,keepAliveTime参数也会起作用,直到线程池中的线程数为0；
     * @param unit 参数keepAliveTime的时间单位，在TimeUnit类中有7种静态属性：TimeUnit.DAYS,HOURS,MINUTES,SECONDS,MILLISECONDS,MICROSECONDS,NANOSECONDS
     * @param workQueue 阻塞队列，用来存储等待执行的任务，一般来说，这里的阻塞队列有以下几种选择:
     *      ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。
     * @param threadFactory 线程工厂，主要用来创建线程；
     * @param handler 拒绝处理任务时的策略，有以下四种取值：
     *                ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     *                ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     *                ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     *                ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler) {
//        if (corePoolSize < 0 ||
//                maximumPoolSize <= 0 ||
//                maximumPoolSize < corePoolSize ||
//                keepAliveTime < 0)
//            throw new IllegalArgumentException();
//        if (workQueue == null || threadFactory == null || handler == null)
//            throw new NullPointerException();
//        this.acc = System.getSecurityManager() == null ?
//                null :
//                AccessController.getContext();
//        this.corePoolSize = corePoolSize;
//        this.maximumPoolSize = maximumPoolSize;
//        this.workQueue = workQueue;
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        this.threadFactory = threadFactory;
//        this.handler = handler;

    /**
     * 1.execute()方法实际上是Executor中声明的方法，在ThreadPoolExecutor进行了具体的实现，通过这个方法可以向线程池提交一个任务，交由线程池去执行
     * 2.submit()方法是在ExecutorService中声明的方法，AbstractExecutorService就已经有了具体的实现，在ThreadPoolExecutor中并没有对其进行重写，
     * 这个方法也是用来向线程池提交任务的，但是它和execute()方法不同，它能够返回任务执行的结果，去看submit()方法的实现，会发现它实际上还是调用的execute()方法，只不过它利用了Future来获取任务执行结果。
     *　shutdown()和shutdownNow()是用来关闭线程池的。
     */
//    ThreadPoolExecutor.execute();
//    ThreadPoolExecutor.submit();
//    ThreadPoolExecutor.shutdown();
//    ThreadPoolExecutor.shutdownNow();


    public static void main(String[] args){
        //核心线程数3 最大线程数5  队列里面8 所以一共有13个任务可执行其余7个丢掉
        //可以看到现有3个核心线程执行任务,再有任务会放到消息队列,等到消息队列满了以后会增加线程数量.线程数量达到最大值丢弃任务
        ThreadPoolExecutor tExecutor = new ThreadPoolExecutor(3,5,50
                ,TimeUnit.SECONDS,new LinkedBlockingDeque<>(8),new ThreadPoolExecutor.DiscardPolicy());
        for (int z=1;z <=20;z++){
            tExecutor.execute(new  MyRunnable(z));
            System.out.println("线程池中线程数目："+tExecutor.getPoolSize()+"，队列中等待执行的任务数目："+
                    tExecutor.getQueue().size()+"，已执行完别的任务数目："+tExecutor.getCompletedTaskCount());
        }






    }

    private static class MyRunnable implements Runnable{
        volatile int num;

        public MyRunnable(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("执行任务的线程编号为:"+num);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num+"执行完毕");
        }
    }


}
