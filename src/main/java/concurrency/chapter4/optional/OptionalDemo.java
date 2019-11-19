 package concurrency.chapter4.optional;
 
 import java.util.Optional;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class OptionalDemo
 {
   public static void main(String[] args) {
    String name = "woshi shabi";
     
     Optional<String> opt = Optional.ofNullable(name);
 
 
 
 
 
     
     System.out.println(opt.orElse("shabi"));
 
     
     System.out.println(" orElse ");
     opt.orElse(orElse());
     System.out.println(" orElseGet() ");
     
     opt.orElseGet(() -> orElse());
 
     
     whenFilter_thenOk();
   }
 
   
   public static void whenCreateEmptyOptional_thenNull() {
    Optional<User> emptyOpt = Optional.empty();
    emptyOpt.get();
   }
 
 
 
   
   public static void whenCreateOfEmptyOptional_thenNullPointerException() {
    User user = null;
     
    Optional<User> opt = Optional.ofNullable(user);
   }
   
   public static String orElse() {
    System.out.println("orElse 会在非空的时候创造对象 而 orElseGet()则不会 ");
     return "你是傻逼";
   }
 
 
   
  public void whenThrowException_thenOk() { User user = null; }
 
 
 
 
 
 
   
   public static void whenFilter_thenOk() {
    User user = new User();
     
    user.setName("mmb");
     
     Optional<User> result = Optional.ofNullable(user).filter(u -> (u.getName() != null && u.getName().contains("s")));
   }
 }
