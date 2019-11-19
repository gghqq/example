/*    */ package concurrency.chapter6;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadCloseGraceful1
/*    */ {
/*    */   private static class Worker
/*    */     extends Thread
/*    */   {
/*    */     private Worker() {}
/*    */     
/*    */     public void run() {
/*    */       do {
/*    */       
/* 26 */       } while (!Thread.interrupted());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 35 */     Worker worker = new Worker();
/* 36 */     worker.start();
/*    */     try {
/* 38 */       Thread.sleep(10000L);
/* 39 */     } catch (InterruptedException e) {
/* 40 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 43 */     worker.interrupt();
/*    */   }
/*    */ }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter6\ThreadCloseGraceful1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */