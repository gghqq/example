 package concurrency.chapter6;
 
 
 
 
 
 
 
 
 
 
 
 public class ThreadService
 {
   private Thread excuteThread;
   private boolean finished = false;
   
   public void execute(final Runnable task) {
    this.excuteThread = new Thread()
       {
         public void run()
         {
          Thread runner = new Thread(task);
           runner.setDaemon(true);
           
          runner.start();
 
 
           
           try {
            runner.join();
           ThreadService.this.finished = true;
          } catch (InterruptedException interruptedException) {}
         }
       };
 
     
    this.excuteThread.start();
   }
   
   public void shutDown(long mills) {
    long currentTime = System.currentTimeMillis();
   while (!this.finished) {
       if (System.currentTimeMillis() - currentTime >= mills) {
       System.out.println(" 任务超时  需要结束他 ");
       this.excuteThread.interrupt();
         
         break;
       } 
       try {
         Thread.sleep(1L);
     } catch (InterruptedException e) {
         System.out.println("执行线程被打断!");
         
         break;
       } 
     } 
   this.finished = false;
   }
 }

