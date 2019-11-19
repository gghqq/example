 package concurrency.chapter7;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class BankWindowVersion1
 {
   public static void main(String[] args) {
    SynchronizeRunnable syncherizedRunnable = new SynchronizeRunnable();
     (new Thread(syncherizedRunnable, "一号柜台")).start();
     (new Thread(syncherizedRunnable, "二号柜台")).start();
     (new Thread(syncherizedRunnable, "三号柜台")).start();
   }
 }

