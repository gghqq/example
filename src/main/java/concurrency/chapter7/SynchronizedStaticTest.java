 package concurrency.chapter7;
 
 
 
 
 
 
 
 
 public class SynchronizedStaticTest
 {
   public static void main(String[] args) {
    (new Thread("T1")
       {
         public void run() {
         SynchronizedStatic.m1();
         }
      }).start();
    (new Thread("T2")
       {
         public void run() {
         SynchronizedStatic.m2();
         }
      }).start();
   }
 }


