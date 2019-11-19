 package concurrency.chapter2.TaxCalaculator;
 

 
 
 public class TaxCalaculator
 {
   private final double salary;
   private final double bonus;
   private TaxService taxService;
   
   public TaxCalaculator(double salary, double bonus) {
     this.salary = salary;
    this.bonus = bonus;
   }
 
 
 
 
 
 
   
   protected Double tax() { return this.taxService.tax1(this.salary, this.bonus); }
 
   
   public Double tax1() {
    System.out.println("这是加入的业务逻辑");
    return tax();
   }
 
   
  public void setTaxService(TaxService taxService) { this.taxService = taxService; }
 }

