 package concurrency.chapter6;
 
 
 
 
 
 
 
 
 
 public class ThreadCloseGraceful
 {
   private static class Worker
     extends Thread
   {
     private volatile boolean start = true;
     
     public void run() {
      while (this.start);
     }
 
 
 
     
     public void shutDown() { this.start = false; }
     
     private Worker() {}
   }
   
   public static void main(String[] args) {
     Worker worker = new Worker();
    worker.start();
     try {
      Thread.sleep(10000L);
    } catch (InterruptedException e) {
       e.printStackTrace();
     } 
     
     worker.shutDown();
   }
 }


