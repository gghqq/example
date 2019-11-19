 package concurrency.chapter7;
 
 
 
 
 
 
 
 
 public class SynchronizeRunnable
   implements Runnable
 {
   private final int MAX = 500;
  private int index = 1;
 
 
 
 
 
 
 
 
 
 
   
   public void run() {
     do {
     
    } while (!ticket());
   }
 
 
 
   
   private synchronized boolean ticket() {
   if (this.index > 500) {
      return true;
     }
    System.out.println("当前柜台 : " + Thread.currentThread().getName() + "取票号为 : " + this.index++);
    return false;
   }
 }

