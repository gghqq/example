 package concurrency.chapter6;
 
 
 
 
 
 
 
 
 
 
 public class ThreadCloseGraceful2
 {
   public static void main(String[] args) {
     ThreadService service = new ThreadService();
    long start = System.currentTimeMillis();
    service.execute(() -> {
 
           
           try {
             Thread.sleep(5000L);
          } catch (InterruptedException e) {
             e.printStackTrace();
           } 
         });
     
    service.shutDown(10000L);
   System.out.println(System.currentTimeMillis() - start);
   }
 }

