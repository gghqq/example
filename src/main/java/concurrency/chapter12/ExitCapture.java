package concurrency.chapter12;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/22
 * @Time: 10:26
 * @Description:  线程结束时或者出现问题  捕获信号 进行某些操作 例如会释放资源
 **/
public class ExitCapture {



    public static void main(String[] args)
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(" The application will be exit");
            notifyAndRelease();
        }));
        int i = 0;
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println(" I am working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i ++ ;
            if (i > 20 ) throw  new RuntimeException();
        }


    }

    private static void notifyAndRelease() {
        System.out.println("notify to the admin ");
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( " Will release resource (socket, file , connection) ");

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}