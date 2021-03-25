package 数据结构.queue;

import com.google.common.collect.Queues;

import java.util.concurrent.*;

/**
 * @ClassName Basket
 * @Description :
 *      非阻塞队列：ConcurrentLinkedQueue(无界线程安全)，采用CAS机制（compareAndSwapObject原子操作）。
 *      阻塞队列： 采用锁机制；。
 * 　　* ArrayBlockingQueue ：一个由数组支持的有界队列。
 * 　　* LinkedBlockingQueue ：一个由链接节点支持的可选有界队列。 使用锁机制 底层获取锁也是使用的CAS算法
 * 　　* PriorityBlockingQueue ：一个由优先级堆支持的无界优先级队列。
 * 　　* DelayQueue ：一个由优先级堆支持的、基于时间的调度队列。
 * 　　* SynchronousQueue ：一个利用 BlockingQueue 接口的简单聚集（rendezvous）机制。
 *
 * java 1.5以后阻塞队列的操作
 *     add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
 * 　　remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
 * 　　element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
 * 　　offer       添加一个元素并返回true       如果队列已满，则返回false
 * 　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
 * 　　peek       返回队列头部的元素             如果队列为空，则返回null
 * 　　put         添加一个元素                      如果队列满，则阻塞
 * 　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 * @Author agan
 * @Date 2021/3/16 11:37
 **/

public  class BlockQueue {

    public static class Basket{
        //定义一个只能放3个水果的篮子
        BlockingQueue<String> baskets= Queues.newArrayBlockingQueue(3);
//        生产苹果，放入篮子
        private  void addQueue() throws InterruptedException {
            //往篮子里放一个水果,如果队列满,则等到有位置
            baskets.put("水果");
        }
//        消费苹果,拿出篮子,获得锁
        private String eatQueue() throws InterruptedException {
//            final ReentrantLock lock = this.lock;
//            lock.lockInterruptibly();
            //从篮子里面拿出一个水果  如果篮子里没有,就等待篮子里有水果
          return   baskets.take();
        }

        public  int getQueueSize(){
            return  baskets.size();
        }


    }
    // 定义苹果生产者
    public static void testBasket(){
//    对于一个final变量，如果是基本数据类型的变量，则其数值一旦在初始化之后便不能更改；如果是引用类型的变量，则在对其初始化之后便不能再让其指向另一个对象。
        //建立一个水果篮
      final  Basket basket = new Basket();
        //定义苹果生产者
        class Producer implements Runnable{
            @Override
            public void run() {
                while (true){
                    try {
                        //生产苹果
                        System.out.println("生产者准备生产苹果: "+System.currentTimeMillis());
                        basket.addQueue();
                        System.out.println("生产者生产苹果完毕: "+System.currentTimeMillis());
                        System.out.println("生产苹果"+ basket.getQueueSize()+"个");
                        // 休眠300ms
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        class Customer implements Runnable{
            @Override
            public void run() {
                while (true){
                    //消费苹果
                    System.out.println("消费开始消费苹果: " + System.currentTimeMillis());
                    try {
                        basket.eatQueue();
                        System.out.println("消费者消费玩苹果: " + System.currentTimeMillis());
                        System.out.println("消费完苹果还有:" + basket.getQueueSize() +"个");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
//        newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = new Producer();
        Customer customer = new Customer();
        service.submit(producer);
        service.submit(customer);
        //线程运行15秒后所有任务停止
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdownNow();
    }

    public static void  main (String[] args){
        BlockQueue.testBasket();
    }







}
