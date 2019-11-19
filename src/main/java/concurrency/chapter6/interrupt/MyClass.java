 package concurrency.chapter6.interrupt;
 
 

 
 
 
 public class MyClass
 {
   public static void main(String[] args0) throws InterruptedException {
     ThreadB threadB = new ThreadB();
     threadB.start();
     System.out.print("main :" + Thread.currentThread().isInterrupted() + "\n");
   }
 }


