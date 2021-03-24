package concurrency.chapter13;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/22
 * @Time: 15:19
 * @Description:
 **/
public class ThreadGroupApi {

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                try {

                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        //use the parent and group name
        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
        Thread t2 = new Thread(tg1, "t2") {
            @Override
            public void run() {
                try {


                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();

        //获取到时可能有些线程生命周期已经结束
        System.out.println(tg1.activeCount());
        System.out.println(tg1.activeGroupCount());

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Thread[] ts1 = new Thread[tg1.activeCount()];
        tg1.enumerate(ts1);
        Arrays.asList(ts1).forEach(System.out::println);
//        //都中断了
//        tg1.interrupt();
    }
}


