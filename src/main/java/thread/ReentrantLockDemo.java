package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLock   https://blog.csdn.net/fuyuwei2015/article/details/83719444
 * @Description
 * ReentrantLock主要利用CAS+AQS队列来实现。它支持公平锁和非公平锁，两者的实现类似。
 * ReentrantLock的基本实现可以概括为：先通过CAS尝试获取锁。如果此时已经有线程占据了锁，那就加入AQS队列并且被挂起。当锁被释放之后，排在CLH队列队首的线程会被唤醒，然后CAS再次尝试获取锁。
 * 在这个时候，如果：
 * 非公平锁：如果同时还有另一个线程进来尝试获取，那么有可能会让这个线程抢先获取；
 * 公平锁：如果同时还有另一个线程进来尝试获取，当它发现自己不是在队首的话，就会排到队尾，由队首的线程获取到锁。

 * CAS(CompareAndSwap)cas有三个操作数,内存值V,预期值A,替换值B.当预期值A和内存值V一致时才替换为B.该操作是一个原子操作(不可被中断的一个或一系列操作).
 * Java中,CAS主要是由sun.misc.Unsafe这个类通过JNI调用CPU底层指令实现.
 * ABA问题(原来是A,修改为B后又改为A),解决思路,加标识.   2.循环时间长开销大
 *
 *【AQS】 是一个用于构建锁和同步容器的框架。事实上concurrent包内许多类都是基于AQS构建，例如ReentrantLock，Semaphore，CountDownLatch，ReentrantReadWriteLock，FutureTask等。。
 *AQS使用一个FIFO的队列表示排队等待锁的线程，队列头节点称作“哨兵节点”或者“哑节点”，它不与任何线程关联。其他的节点与等待线程关联，每个节点维护一个等待状态waitStatus
 *
 * 可重入锁。可重入锁是指同一个线程可以多次获取同一把锁。ReentrantLock和synchronized都是可重入锁。
 * 可中断锁。可中断锁是指线程尝试获取锁的过程中，是否可以响应中断。synchronized是不可中断锁，而ReentrantLock则提供了中断功能。
 *
 * @Author agan
 * @Date 2021/4/1 21:08
 **/

public class ReentrantLockDemo {

    /**
     * 从源码可以看出 ReentrantLock 有三个静态内部类, Sync,FairSync和NonfairSync,其中FairSync和NonfairSync继承Sync,而Sync继承AQS(AbstractQueuedSynchronizer)
     * 这三个类主要实现了AQS的tryRelease(int)和tryAcquire(int) 在Sync里面重写了 nonFairAcquire和 tryRelease
     */
//        默认构造函数  创建了NonfairSync对象 此时锁为非公平锁
   static   ReentrantLock lock = new ReentrantLock();


    /**
     *  通过cas操作判断state是否为0(锁未被获取状态),设置为占用同时设置当前线程为独占锁的线程.unfair也体现在这里,state假如刚被释放,当前线程可以立刻获取.\
     *  锁此时为占用状态则进入acquire
     * @param args
     */
//    final void lock() {
//        if (compareAndSetState(0, 1))
//            setExclusiveOwnerThread(Thread.currentThread());
//        else
//            acquire(1);
//    }

    /**
     *
     * @param args
     */
//    public final void acquire(int arg) {
//        if (!tryAcquire(arg) && acquireQueued( addWaiter(Node.EXCLUSIVE),arg) )
//            selfInterrupt();
//    }

    /**
     * 非公平锁第一步,如果锁没有被占用则设置当前线程为独占线程,并置state为1,
     * 第二部,如果当前线程为占用锁的线程,则更新重入次数. 可重入锁体现出来
     * @param args
     */
//    tryAcquire(arg)
//    final boolean nonfairTryAcquire(int acquires) {
//        //获取当前线程
//        final Thread current = Thread.currentThread();
//        //获取state变量值
//        int c = getState();
//        if (c == 0) { //没有线程占用锁
//            if (compareAndSetState(0, acquires)) {
//                //占用锁成功,设置独占线程为当前线程
//                setExclusiveOwnerThread(current);
//                return true;
//            }
//        } else if (current == getExclusiveOwnerThread()) { //当前线程已经占用该锁
//            int nextc = c + acquires;
//            if (nextc < 0) // overflow
//                throw new Error("Maximum lock count exceeded");
//            // 更新state值为新的重入次数
//            setState(nextc);
//            return true;
//        }
//        //获取锁失败
//        return false;
//    }

    /**
     * 如果还是获取锁失败,则要入队列
     * 将新节点和当前线程关联并且入队列
     * @param mode 独占/共享
     * @return 新节点
     */
//    private Node addWaiter(Node mode) {
//        //初始化节点,设置关联线程和模式(独占 or 共享)
//        Node node = new Node(Thread.currentThread(), mode);
//        // 获取尾节点引用
//        Node pred = tail;
//        // 尾节点不为空,说明队列已经初始化过
//        if (pred != null) {
//            node.prev = pred;
//            // 设置新节点为尾节点
//            if (compareAndSetTail(pred, node)) {
//                pred.next = node;
//                return node;
//            }
//        }
//        // 尾节点为空,说明队列还未初始化,需要初始化head节点并入队新节点
//        enq(node);
//        return node;
//    }


//    /**
//     * 经典的自旋+CAS组合来实现非阻塞的原子操作。由于compareAndSetHead的实现使用了unsafe类提供的CAS操作，所以只有一个线程会创建head节点成功
//     * 初始化队列并且入队新节点
//     */
//    private Node enq(final Node node) {
//        //开始自旋
//        for (;;) {
//            Node t = tail; 获取尾节点引用
//            if (t == null) { //  需要初始化
//                // 如果tail为空,则新建一个head节点,并且tail指向head
//                if (compareAndSetHead(new Node()))
//                    tail = head;
//            } else {
//                node.prev = t;
//                // tail不为空,将新节点入队
//                if (compareAndSetTail(t, node)) {
//                    t.next = node;
//                    return t;
//                }
//            }
//        }
//    }


    /**
     * 清晰的说明了acquireQueued的执行流程。
     * 已经入队的线程尝试获取锁
     */
//    final boolean acquireQueued(final Node node, int arg) {
//        boolean failed = true; //标记是否成功获取锁
//        try {
//            boolean interrupted = false; //标记线程是否被中断过
//            for (;;) {
//                final Node p = node.predecessor(); //获取前驱节点
//                //如果前驱是head,即该结点已成老二，那么便有资格去尝试获取锁
//                if (p == head && tryAcquire(arg)) {
//                    setHead(node); // 获取成功,将当前节点设置为head节点
//                    p.next = null; // 原head节点出队,在某个时间点被GC回收
//                    failed = false; //获取成功
//                    return interrupted; //返回是否被中断过
//                }
//                // 判断获取失败后是否可以挂起,若可以则挂起
//                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
//                    // 线程若被中断,设置interrupted为true
//                    interrupted = true;
//            }
//        } finally {
//            if (failed)
//                cancelAcquire(node);
//        }
//    }


    /**
     *  此时线程在始终获取锁失败后,在AQS队列里  判断当前线程获取锁失败之后是否需要挂起.
     *  线程入队后能够挂起的前提是，它的前驱节点的状态为SIGNAL
     *  。所以shouldParkAfterFailedAcquire会先判断当前节点的前驱是否状态符合要求，若符合则返回true，然后调用parkAndCheckInterrupt，将自己挂起。
     *  如果不符合，再看前驱节点是否>0(CANCELLED)，若是那么向前遍历直到找到第一个符合要求的前驱，若不是则将前驱节点的状态设置为SIGNAL。
     *   整个流程中，如果前驱结点的状态不是SIGNAL，那么自己就不能安心挂起，需要去找个安心的挂起点，同时可以再尝试下看有没有机会去尝试竞争锁。
     */
//    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
//        //前驱节点的状态
//        int ws = pred.waitStatus;
//        if (ws == Node.SIGNAL)   SIGNAL(示意,信号)
//            // 前驱节点状态为signal,返回true
//            return true;
//        // 前驱节点状态为CANCELLED
//        if (ws > 0) {
//            // 从队尾向前寻找第一个状态不为CANCELLED的节点
//            do {
//                node.prev = pred = pred.prev;
//            } while (pred.waitStatus > 0);
//            pred.next = node;
//        } else {
//            // 将前驱节点的状态设置为SIGNAL
//            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
//        }
//        return false;
//    }
//
//    /**
//     * 挂起当前线程,返回线程中断状态并重置
//     */
//    private final boolean parkAndCheckInterrupt() {
//        LockSupport.park(this);
//        return Thread.interrupted();
//    }


    /**
     * 释放锁
     * @param args
     */
//    public void unlock() {
//        sync.release(1);
//    }
//
//    public final boolean release(int arg) {
//        if (tryRelease(arg)) {
//            Node h = head;
//            if (h != null && h.waitStatus != 0)
//                unparkSuccessor(h);
//            return true;
//        }
//        return false;
//    }

    /**
     * 释放当前线程占用的锁
     * @param releases
     * @return 是否释放成功
     */
//    protected final boolean tryRelease(int releases) {
//        // 计算释放后state值
//        int c = getState() - releases;
//        // 如果不是当前线程占用锁,那么抛出异常
//        if (Thread.currentThread() != getExclusiveOwnerThread())
//            throw new IllegalMonitorStateException();
//        boolean free = false;
//        if (c == 0) {
//            // 锁被重入次数为0,表示释放成功
//            free = true;
//            // 清空独占线程
//            setExclusiveOwnerThread(null);
//        }
//        // 更新state值
//        setState(c);
//        return free;
//    }


//    超时机制
//    public boolean tryLock(long timeout, TimeUnit unit)
//            throws InterruptedException {
//        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
//    }

    /**
     * 如果线程被中断了，那么直接抛出InterruptedException。如果未中断，先尝试获取锁，获取成功就直接返回，获取失败则进入doAcquireNanos。
     * @param args
     */
//    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        if (Thread.interrupted())
//            throw new InterruptedException();
//        return tryAcquire(arg) ||
//                doAcquireNanos(arg, nanosTimeout);
//    }

    /**
     * doAcquireNanos的流程简述为：线程先入等待队列，然后开始自旋，尝试获取锁，获取成功就返回，失败则在队列里找一个安全点把自己挂起直到超时时间过期。
     * 这里为什么还需要循环呢？因为当前线程节点的前驱状态可能不是SIGNAL，那么在当前这一轮循环中线程不会被挂起，然后更新超时时间，开始新一轮的尝试
     * 在有限的时间内去竞争锁
     * @return 是否获取成功
     */
//    private boolean doAcquireNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        // 起始时间
//        long lastTime = System.nanoTime();
//        // 线程入队
//        final Node node = addWaiter(Node.EXCLUSIVE);
//        boolean failed = true;
//        try {
//            // 又是自旋!
//            for (;;) {
//                // 获取前驱节点
//                final Node p = node.predecessor();
//                // 如果前驱是头节点并且占用锁成功,则将当前节点变成头结点
//                if (p == head && tryAcquire(arg)) {
//                    setHead(node);
//                    p.next = null; // help GC
//                    failed = false;
//                    return true;
//                }
//                // 如果已经超时,返回false
//                if (nanosTimeout <= 0)
//                    return false;
//                // 超时时间未到,且需要挂起
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        nanosTimeout > spinForTimeoutThreshold)
//                    // 阻塞当前线程直到超时时间到期
//                    LockSupport.parkNanos(this, nanosTimeout);
//                long now = System.nanoTime();
//                // 更新nanosTimeout
//                nanosTimeout -= now - lastTime;
//                lastTime = now;
//                if (Thread.interrupted())
//                    //相应中断
//                    throw new InterruptedException();
//            }
//        } finally {
//            if (failed)
//                cancelAcquire(node);
//        }
//    }






    public static void main(String[] args){
        lock.lock();
        try {
            //todo
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }
}
