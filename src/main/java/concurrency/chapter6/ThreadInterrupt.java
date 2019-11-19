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
/*    */ public class ThreadInterrupt
/*    */ {
/*    */   public static void main(String[] args) throws InterruptedException {
/* 15 */     Thread thread = new Thread()
/*    */       {
/*    */         public void run() {
/*    */           while (true) {
/*    */             try {
/*    */               while (true)
/* 21 */                 wait(100L);  break;
/* 22 */             } catch (InterruptedException e) {
/* 23 */               System.out.println(isInterrupted());
/* 24 */               e.printStackTrace();
/*    */             } 
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 30 */     thread.start();
/* 31 */     Thread.sleep(1000L);
/* 32 */     System.out.println(thread.isInterrupted());
/* 33 */     thread.interrupt();
/* 34 */     System.out.println(thread.isInterrupted());
/*    */   }
/*    */ }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter6\ThreadInterrupt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */