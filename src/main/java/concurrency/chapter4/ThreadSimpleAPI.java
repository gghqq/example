 package concurrency.chapter4;
 
 import java.util.Optional;
 
 
 
 
 
 
 
 
 
 public class ThreadSimpleAPI
 {
   public static void main(String[] args) {
    Thread thread = new Thread(() -> {
         Optional.of("hello").ifPresent(System.out::println);
           try {
            Thread.sleep(100000L);
          } catch (InterruptedException e) {
            e.printStackTrace();
           } 
         },"t1");
     thread.start();
    Optional.of(Long.valueOf(thread.getId())).ifPresent(System.out::println);
    Optional.of(thread.getName()).ifPresent(System.out::println);
    Optional.of(Integer.valueOf(thread.getPriority())).ifPresent(System.out::println);
   }
 }


