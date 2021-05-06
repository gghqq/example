package JVM.创建对象的过程;

/**
 * @ClassName InitialTest
 * @Description
 * @Author agan
 * @Date 2021/5/3 22:03
 **/

public class InitialTest {
    private  static  String s = "静态变量";

    private  String s1  = "普通变量";

    private static final  String s2 = "常量";

    static {
        System.out.println(s);
        System.out.println(s2);
        System.out.println("静态代码块");
    }

    {
        System.out.println(s1);
        System.out.println("初始化块");
    }

    private InitialTest(){
        System.out.println("类构造器");
    };

    public static void main(String[] args) {
        new InitialTest();
    }
}
