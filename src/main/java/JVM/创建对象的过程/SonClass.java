package JVM.创建对象的过程;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName SonClass
 * @Description
 * @Author agan
 * @Date 2021/5/3 22:13
 **/

public class SonClass extends FatherClass {

    private static String f_s1 = "子类---静态变量";
    private final static String f_s2 = "子类---静态常量";
    private String f_s3 = "子类---普通变量";

    static {
        System.out.println(f_s1);
        System.out.println(f_s2);
        System.out.println("子类---静态初始化代码块");
    }

    {
        System.out.println(f_s3);
        System.out.println("子类---初始化代码块");
    }

    private SonClass(){
        super();
        System.out.println("子类---构造器");
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
        System.out.println("子类---main方法");
        final SonClass sonClass = new SonClass();//new
        SonClass.class.newInstance(); //class对象的newInstance()
        Constructor<SonClass> constructor = SonClass.class.getConstructor();
        constructor.newInstance();//构造函数对象的newInstance;
        sonClass.clone(); //Object的clone
    }

//父类---静态变量
//父类---静态常量
//父类---静态初始化代码块
//子类---静态变量
//子类---静态常量
//子类---静态初始化代码块
//子类---main方法
//父类---普通变量
//父类---初始化代码块
//父类---构造器
//子类---普通变量
//子类---初始化代码块
//子类---构造器

}
