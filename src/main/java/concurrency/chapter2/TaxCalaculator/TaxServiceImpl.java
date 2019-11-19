 package concurrency.chapter2.TaxCalaculator;








 public class TaxServiceImpl
   implements TaxService
 {
   private static final Double DOUBLE1 = Double.valueOf(0.1D);
  private static final Double DOUBLE2 = Double.valueOf(0.2D);



   public Double tax1(double salary, double bonus) { return Double.valueOf(salary * DOUBLE1.doubleValue() + bonus * DOUBLE2.doubleValue()); }
 }

