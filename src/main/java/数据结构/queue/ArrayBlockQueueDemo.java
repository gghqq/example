package 数据结构.queue;

import com.google.common.collect.Lists;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Array
 * @Description https://www.cnblogs.com/skywang12345/p/3498652.html
 * ArrayBlockQueue是有限的阻塞队列,是通过数组+ReentrantLock,公平性是通过ReentrantLock来实现的
 * 他的新增是通过实现Queue的offer方法,使用了ReentranLock上锁解锁来实现的
 *
 * 1. ArrayBlockingQueue继承于AbstractQueue，并且它实现了BlockingQueue接口。
 * 2. ArrayBlockingQueue内部是通过Object[]数组保存数据的，也就是说ArrayBlockingQueue本质上是通过数组实现的。ArrayBlockingQueue的大小，即数组的容量是创建ArrayBlockingQueue时指定的。
 * 3. ArrayBlockingQueue与ReentrantLock是组合关系，ArrayBlockingQueue中包含一个ReentrantLock对象(lock)。ReentrantLock是可重入的互斥锁，ArrayBlockingQueue就是根据该互斥锁实现“多线程对竞争资源的互斥访问”。而且，ReentrantLock分为公平锁和非公平锁，关于具体使用公平锁还是非公平锁，在创建ArrayBlockingQueue时可以指定；而且，ArrayBlockingQueue默认会使用非公平锁。
 * 4. ArrayBlockingQueue与Condition是组合关系，ArrayBlockingQueue中包含两个Condition对象(notEmpty和notFull)。而且，Condition又依赖于ArrayBlockingQueue而存在，通过Condition可以实现对ArrayBlockingQueue的更精确的访问 -- (01)若某线程(线程A)要取数据时，数组正好为空，则该线程会执行notEmpty.await()进行等待；当其它某个线程(线程B)向数组中插入了数据之后，会调用notEmpty.signal()唤醒“notEmpty上的等待线程”。此时，线程A会被唤醒从而得以继续运行。(02)若某线程(线程H)要插入数据时，数组已满，则该线程会它执行notFull.await()进行等待；当其它某个线程(线程I)取出数据之后，会调用notFull.signal()唤醒“notFull上的等待线程”。
 *
 *
 *
 * @Author agan
 * @Date 2021/4/14 22:00
 **/

public class ArrayBlockQueueDemo {
//    默认构造函数指定数组大小
    private static ArrayBlockingQueue a = new ArrayBlockingQueue(10);

    public static void main (String[] args) throws InterruptedException {
        // 移除此队列中所有可用的元素，并将它们添加到给定 collection 中。
        a.drainTo(Lists.newArrayList("1","2"));
        // 最多从此队列中移除给定数量的可用元素，并将这些元素添加到给定 collection 中。
        a.drainTo(Lists.newArrayList("1","2"),0);
        // 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），在成功时返回 true，如果此队列已满，则返回 false。
        a.offer("3");
        // 将指定的元素插入此队列的尾部，如果该队列已满，则在到达指定的等待时间之前等待可用的空间。
        a.offer("4",10, TimeUnit.SECONDS);
        // 获取但不移除此队列的头；如果此队列为空，则返回 null。
        a.peek();
        // 获取并移除此队列的头，如果此队列为空，则返回 null。
        a.poll();
        // 将指定的元素插入此队列的尾部，如果该队列已满，则等待可用的空间。
        a.put("6");
        // 返回在无阻塞的理想情况下（不存在内存或资源约束）此队列能接受的其他元素数量。
        a.remainingCapacity();
        // 从此队列中移除指定元素的单个实例（如果存在）。
        a.remove("6");
        // 返回此队列中元素的数量。
        a.size();
        // 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
        a.take();
        // 返回一个按适当顺序包含此队列中所有元素的数组。
        a.toArray();


    }

}
