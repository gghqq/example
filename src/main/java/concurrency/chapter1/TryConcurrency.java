package concurrency.chapter1;



public class TryConcurrency
{
  public static void main(String[] args) {
    (new Thread("Read-thread")
    {
      public void run() {
        TryConcurrency.readFromDataBase();
      }
    }).start();
    (new Thread("Write-thread")
    {
      public void run() {
        TryConcurrency.writeDataToFile();
      }
    }).start();
  }



  private static void readFromDataBase() {
    try {
      println("Begin read data from db");
      Thread.sleep(200000L);
      println("Read data done and start handle it,");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    println("The data handle finish and successfully");
  }


  private static void writeDataToFile() {
    try {
      println("Begin write data from db");
      Thread.sleep(200000L);
      println("Write data done and start handle it,");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    println("The data handle finish and successfully");
  }



  private static void println(String message) { System.out.println(message); }
}

