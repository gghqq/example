 package concurrency.chapter4;
 
 
 
 
 
 
 
 
 public class DaemonThread
 {
   public static void main(String[] args) {
     Thread thread = new Thread()
       {
         public void run() {
           try {
            System.out.println(Thread.currentThread().getName() + " run ");

            Thread.sleep(100000L);
             
            System.out.println(Thread.currentThread().getName() + " dead");
           }
           catch (InterruptedException e) {
            e.printStackTrace();
           } 
         }
       };
 
     
     thread.setDaemon(true);
     thread.start();
     try {
      Thread.sleep(2000L);
     } catch (InterruptedException e) {
      e.printStackTrace();
     } 
    System.out.println(Thread.currentThread().getName());
   }
 }

