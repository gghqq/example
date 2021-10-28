package 系统设计相关.设计模式.策略模式.传统习惯;

/**
 * @ClassName Xiaomi
 * @Description
 * @Author wpj
 * @Date 2021/10/21 21:26
 **/

public class Xiaomi  extends Phone{
    @Override
    public void xitong() {
        System.out.println("~~~安卓系统");
    }

    @Override
    public void pinpai() {
        System.out.println("~~~xiaomi");
    }
}
