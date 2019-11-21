package concurrency.chapter9;

import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/20
 * @Time: 15:23
 * @Description:
 **/
public class DifferenceOfWaitAndSleep {

    private static   final Object MONITOR = new Object();

    public static void main(String[] args) {
    //    m1();
//    m2();
        Stream.of("T1", "T2").forEach(name -> new Thread(name){
            @Override
            public void run() {

              m2();
            }
        }.start());
    }

    public static void m1(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void m2(){
        synchronized (MONITOR){
            try {
                //sleep 不会释放锁  但是wait会
                System.out.println("The Thread"+ Thread.currentThread().getName() + " enter ");
                MONITOR.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void m3(){
        synchronized (MONITOR) {
            try {
                System.out.println("The Thread"+ Thread.currentThread().getName() + " enter ");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}