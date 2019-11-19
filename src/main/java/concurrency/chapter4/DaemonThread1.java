package concurrency.chapter4;








public class DaemonThread1
{
    public static void main(String[] args) {
        Thread thread = new Thread()
        {
            public void run() {
                Thread t = new Thread()
                {
                    public void run() {
                        while (true) {
                            System.out.println(" do some thing");
                            try {
                                Thread.sleep(10000L);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                };

                t.start();
            }
        };



        thread.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

