package concurrency.chapter9;


import java.util.stream.Stream;

/***
 * @description:
 * @param
 * @return:
 * @author: Andy
 * @date: 2019/11/20  14:32
 */
public class ProduceConsumerVersion3 {
    private int i = 1;

    private final Object LOCK = new Object();

    private volatile boolean pflag = false;


    private void produce() {
        synchronized (LOCK) {
           while (pflag) {  // 如果使用if    两个线程被同时唤醒  但是只有一个能拿到锁  假如第一个线程拿到锁 被唤醒 继续往下执行生产完毕后 whit    第二个唤醒后也会继续执行 重复生产
               //使用while的话会 再次检查 flag  此时以第一个线程生产后已为true  所以不会再次生产 避免重复生产
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println(Thread.currentThread().getName() +" ---->  " + i);
            pflag = true;
            LOCK.notifyAll();
        }
    }

    private void consumer() {
        synchronized (LOCK) {
            //如果没有数据  则what
            while (!pflag) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + this.i);
            LOCK.notifyAll();
            pflag = false;
        }
    }


    public static void main(String[] args) {
        final ProduceConsumerVersion3 pc = new ProduceConsumerVersion3();
       Stream.of("P1","P2").forEach(n -> new Thread(n) {
           @Override
           public void run() {
               while (true) {
                   pc.produce();
               }
           }
       }.start());
       Stream.of("C1","C2").forEach(n -> new Thread(n) {
           @Override
           public void run() {
               while (true) {
                   pc.consumer();
               }
           }
       }.start());

    }
}