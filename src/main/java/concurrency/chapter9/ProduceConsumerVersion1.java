 package concurrency.chapter9;
 
 

 
 public class ProduceConsumerVersion1
 {
   private int i = 1;
   private final Object LOCK = new Object();
   
   private void produce() {
     synchronized (this.LOCK) {
       System.out.println("P  -->" + this.i++);
     } 
   }
   private void consumer() {
     synchronized (this.LOCK) {
       System.out.println("C  -->" + this.i);
     } 
   }
 
 
   
   public static void main(String[] args) {
     final ProduceConsumerVersion1 pc = new ProduceConsumerVersion1();
     (new Thread("P")
       {
         @Override
         public void run() {
           while (true) {
             pc.produce();
           }
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