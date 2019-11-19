package concurrency.chapter1;









public class TemplateMethod
{
    public final void print(String message) {
        System.out.println(" ############## ");

        wrapPrint(message);

        System.out.println(" ############## ");
    }


    protected void wrapPrint(String message) {}


    public static void main(String[] args) {
        (new TemplateMethod()
        {
            protected void wrapPrint(String message) {
                System.out.println("!!!!" + message + "!!!!!");
            }
        }).print("hello");


        (new TemplateMethod()
        {
            protected void wrapPrint(String message) {
                System.out.println("++++" + message + "++++");
            }
        }).print("hello");
    }
}


