 package concurrency.chapter7;
 
 

 class ThisLock
 {
  private final Object object = new Object();
 
   
   public synchronized void m1() {
     try {
      System.out.println(Thread.currentThread().getName());
      Thread.sleep(1000L);
     } catch (InterruptedException e) {
      e.printStackTrace();
     } 
   }
 
 
   
   public void m2() {
   synchronized (this.object) {
       try {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
       e.printStackTrace();
       } 
     } 
   }
 }


