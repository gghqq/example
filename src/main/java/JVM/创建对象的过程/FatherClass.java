package JVM.创建对象的过程;

/**
 * @ClassName FaterClass
 * @Description
 * @Author agan
 * @Date 2021/5/3 22:09
 **/

public class FatherClass {
    private static String f_s1 = "父类---静态变量";
    private final static String f_s2 = "父类---静态常量";
    private String f_s3 = "父类---普通变量";

    static {
        System.out.println(f_s1);
        System.out.println(f_s2);
        System.out.println("父类---静态初始化代码块");
    }

    {
        System.out.println(f_s3);
        System.out.println("父类---初始化代码块");
    }

    public FatherClass(){
        System.out.println("父类---构造器");
    }




}
