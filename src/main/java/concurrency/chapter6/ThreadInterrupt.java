package concurrency.chapter6;


public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        while (true) {
                            wait(100L);
                            break;
                        }
                    } catch (InterruptedException e) {
                        System.out.println(isInterrupted());
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();
        Thread.sleep(1000L);
        System.out.println(thread.isInterrupted());
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }
}

