package concurrency.chapter11;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/21
 * @Time: 14:58
 * @Description:
 **/
public class SynchronizedProblem {

    public static void main(String[] args) throws InterruptedException {
        new Thread("T1"){
            @Override
            public void run() {
                System.out.println("T1");
              SynchronizedProblem.run();
            }
        }.start();

        Thread.sleep(5000);

        Thread t2 =  new Thread(){
            @Override
            public void run() {
                SynchronizedProblem.run();
            }
        };
        t2.start();
        Thread.sleep(2000);
        //等待2秒  T2无法打断T1 的状态
        t2.interrupt();
        System.out.println(t2.isInterrupted());
    }


    private synchronized    static  void run(){
        System.out.println(Thread.currentThread().getName());
        while (true){

        }
    }
}