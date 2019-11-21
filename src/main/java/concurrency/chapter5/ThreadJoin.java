 package concurrency.chapter5;
 
 import java.util.stream.IntStream;
 
 

 public class ThreadJoin
 {
   public static void main(String[] args) throws InterruptedException {
    Runnable runnable1 = new Runnable()
       {
           @Override
         public void run() {
          IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName().concat(" 线程----------->  ") + i));
         }
       };
 
     
    Thread thread = new Thread(runnable1);
     Thread thread1 = new Thread(runnable1);
    thread1.start();
   thread.start();
   thread1.join();
    thread.join();
    IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName().concat(" 线程----------->  ") + i));
   }
 }

