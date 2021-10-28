package 系统设计相关.设计模式.策略模式.传统习惯;

/**
 * @ClassName Phone
 * @Description
 * @Author wpj
 * @Date 2021/10/21 21:20
 **/

public abstract class Phone {
    public void takePhoto(){
        System.out.println("~~拍照");
    }

    public abstract void xitong();
    public abstract void pinpai();
}
