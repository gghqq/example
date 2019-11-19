 package concurrency.chapter2;
 
 
 
 
 
 
 
 
 
 
 public class Ticket
   extends Thread
 {
   private String name;
   private static final int MAX = 50;
   private static int index = 1;
 
   
   public Ticket(String name) { this.name = name; }
 
 
   
   public void run() {
    while (index <= 50)
       System.out.println("柜台号 " + this.name + " 当前取票号为:  " + index++);
   }
 }

