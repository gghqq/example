 package concurrency.chapter2;
 
 
 
 
 
 
 
 
 public class TicketRunnable
   implements Runnable
 {
  private final int MAX = 500;
   private int index = 1;
 
   
   public void run() {
     while (this.index <= 500) {
       try {
         System.out.println("当前柜台 : " + Thread.currentThread().getName() + "取票号为 : " + this.index++);
      Thread.sleep(5L);
      } catch (InterruptedException e) {
        e.printStackTrace();
       } 
     } 
   }
 }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter2\TicketRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */