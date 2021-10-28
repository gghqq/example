package 系统设计相关.设计模式.策略模式.传统习惯;

/**
 * @ClassName Iphone
 * @Description
 * @Author wpj
 * @Date 2021/10/21 21:24
 **/

public class Iphone  extends Phone{
    @Override
    public void xitong() {
        System.out.println("~~~IOS系统");
    }

    @Override
    public void pinpai() {
        System.out.println("~~~苹果");
    }
}
