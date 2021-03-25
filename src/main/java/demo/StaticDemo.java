package demo;

/**
 * @ClassName StaticDemo
 * @Description
 * static的创建独立于具体对象的域变量或者方法。 即使没有创建对象，也能使用属性和调用方法
 * 只会在类加载的时候执行一次
 * 因为static是被类的实例对象所共享，static变量是被所有对象所共享的，那么这个成员变量就应该定义为静态变量 。
 * 静态变量,静态方法,静态代码块,只能修饰内部类(静态类),静态导包
 * 1、静态只能访问静态。 2、非静态既可以访问非静态的，也可以访问静态的。
 * @Author agan
 * @Date 2021/3/25 20:33
 **/

public class StaticDemo {

    static String ss = "多个对象共享该静态变量";
    String s = "非静态";

    private static void sout(){
        System.out.println("不用创建对象也能调用方法");
    }

    public static void main(String[] args){
        StaticDemo.sout();
//        System.out.println(s);//静态无法访问非静态
    }
}
