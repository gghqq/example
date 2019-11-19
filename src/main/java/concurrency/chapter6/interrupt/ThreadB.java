 package concurrency.chapter6.interrupt;
 
 
 
 

 
 public class ThreadB
   extends Thread
 {
   public void run() {
    super.run();
     System.out.print("ThreadB isInterrupted :" + isInterrupted() + "\n");
     System.out.print("ThreadB interrupted :" + interrupted() + "\n");
     interrupt();
     System.out.print("ThreadB isInterrupted :" + isInterrupted() + "\n");
     System.out.print("ThreadB isInterrupted :" + isInterrupted() + "\n");
    System.out.print("ThreadB interrupted :" + interrupted() + "\n");
     System.out.print("ThreadB interrupted :" + interrupted() + "\n");
   }
 }

