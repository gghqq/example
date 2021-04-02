package demo;

/**
 * @ClassName FinalDemo
 * @Description
 *
 * 1.被final修饰的类不可以继承
 * 2.被final修饰的方法不能重写
 * 3.被final修饰的变量指针的引用地址不能更改,但是指向的内容可以更改;
 * 4.被final修饰的常量不能更改
 * String本身就是被final修饰的类
 * @Author agan
 * @Date 2021/3/24 21:03
 **/

public class FinalDemo {

    public FinalDemo(){};

    private static final StringBuilder s = new StringBuilder();

    private static  final int inta = 1;
    public static void main(String[] args){
//        inta = 2; //被final修饰的常量不能更改
        s.append("指向的内容可以更改");
//        s = new StringBuilder("引用不能修改");
        String s = "String本身就是被final修饰的类";
    }
}

//class A{
//    public  final void getString(){}
//}
//class B extends A{
//    @Override
//    public void getString(){
//        System.out.println("被final修饰的方法不能被重写");
//    }
//}

//final class A{}
//class B extends A{}
