package concurrency.chapter13;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/22
 * @Time: 15:04
 * @Description:
 **/
public class ThreadGroupCreate {
    public static void main(String[] args) {
        //use the name
        // 默认是main线程的ThreadGroup  优先级是10
        ThreadGroup tg1= new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1,"t1"){
            @Override
            public void run() {
                try {
                    System.out.println(getThreadGroup().getName());
                    System.out.println(getThreadGroup().getParent());

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        //use the parent and group name
        ThreadGroup tg2= new ThreadGroup(tg1,"TG2");
        System.out.println(tg2.getName());
        System.out.println(tg2.getParent());






//        System.out.println(Thread.currentThread().getName());
//        System.out.println(Thread.currentThread().getThreadGroup().getName());

    }
}