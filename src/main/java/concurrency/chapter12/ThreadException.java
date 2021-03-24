package concurrency.chapter12;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/22
 * @Time: 14:44
 * @Description:
 **/
public class ThreadException {

    private final static  int A =10;
    private final static  int B= 0;

    public static void main(String[] args) {
        //线程里面的异常只能被捕获,无法抛出
        Thread t = new Thread( () -> {
            try {
                Thread.sleep(3_000);
                int result = A/B;
                System.out.println(" result = " +  result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //这里可以处理线程异常
        t.setUncaughtExceptionHandler( (thread,e) -> {
            System.out.println(e);
            System.out.println(thread);
        } );
        t.start();

    }
}