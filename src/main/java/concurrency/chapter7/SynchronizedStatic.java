/*    */ package concurrency.chapter7;
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
/*    */ public class SynchronizedStatic
/*    */ {
/*    */   static  {
/* 15 */     synchronized (SynchronizedStatic.class) {
/* 16 */       System.out.println("static" + Thread.currentThread().getName());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void m1() {
/* 22 */     System.out.println(Thread.currentThread().getName());
/*    */     try {
/* 24 */       Thread.sleep(10000L);
/* 25 */     } catch (InterruptedException e) {
/* 26 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   public static synchronized void m2() {
/* 30 */     System.out.println(Thread.currentThread().getName());
/*    */     try {
/* 32 */       Thread.sleep(10000L);
/* 33 */     } catch (InterruptedException e) {
/* 34 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter7\SynchronizedStatic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */