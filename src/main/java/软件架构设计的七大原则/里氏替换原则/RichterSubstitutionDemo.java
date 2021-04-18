package 软件架构设计的七大原则.里氏替换原则;

/**
 * @ClassName RichterSubstitution
 * @Description
 *   正方形继承长方形,但自己的宽高都是边长,增加长或宽都是增加边长
 *   设计问题导致增长时比较宽高会陷入循环.违背了里氏替换原则，将父类替换为子类后，程序运行结果没有达到预期。
 *   1、子类可以实现父类的抽象方法，但不能覆盖父类的非抽象方法。
 *   2、子类中可以增加自己特有的方法。
 *   3、当子类的方法重载父类的方法时，方法的前置条件（即方法的输入/入参）要比父类方法的输入参数更宽松。
 *   4、当子类的方法实现父类的方法时（重写/重载或实现抽象方法），方法的后置条件（即方法的输出/返回值）要比父类更严格或相等。

 *
 * @Author agan
 * @Date 2021/4/18 22:41
 **/

public class RichterSubstitutionDemo {
    public static void resize(Quadrangle rectangle){
        while (rectangle.getWidth() >= rectangle.getHeight()){
//            长方形的宽应该大于等于高，我们让高一直自增，知道高等于宽变成正方形
//                rectangle.setHeight(rectangle.getHeight() + 1); //当把参数改为四边形会提示标错
            System.out.println("width:"+rectangle.getWidth() + "  height: "+rectangle.getHeight());
        }
        System.out.println("resize结束:\nwidth:"+rectangle.getWidth()+",height:"+rectangle.getHeight());
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(10);
        rectangle.setWidth(20);
        resize(rectangle);//运行后发现高比宽大,对于长方形是合理的
        Square square = new Square();
        square.setLength(10);
        resize(square);//进入死循环  设计不合理.
    }
}
