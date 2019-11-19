/*    */ package concurrency.chapter6.interrupt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadA
/*    */   extends Thread
/*    */ {
/*    */   public void run() {
/* 14 */     super.run();
/*    */     try {
/* 16 */       System.out.println("in ThreadA - about to sleep for 20 seconds :" + isInterrupted());
/* 17 */       Thread.sleep(20000L);
/* 18 */       System.out.println("in ThreadA - wake up");
/* 19 */     } catch (InterruptedException e) {
/* 20 */       System.out.println("in ThreadA - interrupted while sleeping");
/* 21 */       System.out.println("in ThreadA - interrupt status of threadA :" + isInterrupted());
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 26 */     System.out.println("in ThreadA - leaving normally");
/*    */   }
/*    */ }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter6\interrupt\ThreadA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */