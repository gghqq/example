 package concurrency.chapter9;
 
 
 
 
 
 
 
 
 
 
 
 public class ProduceConsumerVersion2
 {
   private int i = 1;
   
   private final Object LOCK = new Object();
   
   private volatile boolean pflag = false;
 
   
   private void produce() {
     synchronized (this.LOCK) {
       if (this.pflag) {
         try {
           this.LOCK.wait();
         } catch (InterruptedException e) {
           e.printStackTrace();
         } 
       } else {
         this.i++;
         System.out.println("P  -->" + this.i);
         this.pflag = true;
         this.LOCK.notify();
       } 
     } 
   }
   
   private void consumer() {
     synchronized (this.LOCK) {
       if (this.pflag) {
         System.out.println("C  -->" + this.i);
         this.LOCK.notify();
         this.pflag = false;
       } else {
         try {
           this.LOCK.wait();
         } catch (InterruptedException e) {
           e.printStackTrace();
         } 
       } 
     } 
   }
 
 
 
   
   public static void main(String[] args) {
     final ProduceConsumerVersion2 pc = new ProduceConsumerVersion2();
     (new Thread("P")
       {
         @Override
         public void run() {
           while (true)
             pc.produce(); 
         }
       }).start();
     (new Thread("C")
       {
         @Override
         public void run() {
           while (true) {
             pc.consumer();
           }
           }
       }).start();
   }
}