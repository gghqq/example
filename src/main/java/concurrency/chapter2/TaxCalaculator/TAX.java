package concurrency.chapter2.TaxCalaculator;





public class TAX
{
 public static void main(String[] args) {
  Double args1 = Double.valueOf(10000.0D);
  Double args2 = Double.valueOf(1000.0D);
  TaxService taxService = new TaxServiceImpl();
  TaxCalaculator tax = new TaxCalaculator(args1.doubleValue(), args2.doubleValue());
  tax.setTaxService(taxService);
  System.out.println(tax.tax());
 }
}


