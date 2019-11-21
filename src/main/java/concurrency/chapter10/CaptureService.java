package concurrency.chapter10;
import java.util.*;
import	java.util.ResourceBundle.Control;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/20
 * @Time: 15:57
 * @Description:
 **/
public class  CaptureService  {


     final static  private LinkedList<Control> CONTROLS = new LinkedList<>();

    public static void main(String[] args) {

        List<Thread> worker = new ArrayList<>();

        Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()
                .map(CaptureService :: createThread)
                .forEach(thread -> {
                    thread.start();
                    worker.add(thread);
                });
        worker.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All of capture work finished").ifPresent(System.out::println);
    }


    private  static  Thread createThread(String name){
        return new Thread(() -> {
            Optional.of("The worker [" + Thread.currentThread().getName() +"] begin capture data.").ifPresent(System.out::println);
            synchronized (CONTROLS){
                while (CONTROLS.size() > 5){
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLS.add(new Control());
            }

                Optional.of("The worker [" + Thread.currentThread().getName() + "} is working.,,,").ifPresent(System.out::println);
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (CONTROLS){
                    Optional.of("The worker [" + Thread.currentThread().getName() + "}end capture data .").ifPresent(System.out::println);
               CONTROLS.removeFirst();
               CONTROLS.notifyAll();
                }
        },name);
    }

    private static class Control {

    }
}