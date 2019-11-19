package concurrency.chapter7;


public class TicketRunnable1
        implements Runnable {
    private final int MAX = 500;
    private int index = 1;
    private final Object MONITOR = new Object();


    public void run() {
        while (true) {
            synchronized (this.MONITOR) {
                if (this.index > 500)
                    break;
                try {
                    Thread.sleep(500000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前柜台 : " + Thread.currentThread().getName() + "取票号为 : " + this.index++);
            }
        }
    }
}


