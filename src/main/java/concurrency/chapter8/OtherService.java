 package concurrency.chapter8;
 
 
 
 
 
 
 
 
 
 
 
 public class OtherService
 {
   private DeadLock deadLock;
   private final Object lock = new Object();
   
   public void s1() {
     synchronized (this.lock) {
       System.out.println(" s1========= ");
     } 
   }
   public void s2() {
     synchronized (this.lock) {
       this.deadLock.m2();
       System.out.println(" s2========= ");
     } 
   }
 
 
   
   public void setDeadLock(DeadLock deadLock) { this.deadLock = deadLock; }
}