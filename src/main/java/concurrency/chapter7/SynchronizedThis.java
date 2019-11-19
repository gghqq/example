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
/*    */ public class SynchronizedThis
/*    */ {
/*    */   public static void main(String[] args) {
/* 14 */     final ThisLock thisLock = new ThisLock();
/*    */     
/* 16 */     (new Thread("T1")
/*    */       {
/*    */         public void run() {
/* 19 */           thisLock.m1();
/*    */         }
/* 21 */       }).start();
/* 22 */     (new Thread("T2")
/*    */       {
/*    */         public void run() {
/* 25 */           thisLock.m2();
/*    */         }
/* 27 */       }).start();
/*    */   }
/*    */ }


/* Location:              F:\idea_workspace\demo\concurrency\target\classes\!\com\comcurrency\chapter7\SynchronizedThis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */