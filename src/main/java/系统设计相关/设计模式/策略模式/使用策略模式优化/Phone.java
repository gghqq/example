package 系统设计相关.设计模式.策略模式.使用策略模式优化;

import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.LanYa;
import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.PinPai;
import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.Xitong;

/**
 * @ClassName Iphone
 * @Description :  重新设计
 *
 * @Author wpj
 * @Date 2021/10/21 22:11
 **/

public abstract class Phone {


     LanYa lanYaBehavior;
     PinPai pinpaiBehavior;
     Xitong xitongBehavior;

     public abstract void takePhoto();
     public void pinpai(){
         pinpaiBehavior.pinpai();
     }
     public void xitong(){
         xitongBehavior.xitong();
     }

    public LanYa getLanYaBehavior() {
        return lanYaBehavior;
    }

    public void setLanYaBehavior(LanYa lanYaBehavior) {
        this.lanYaBehavior = lanYaBehavior;
    }

    public PinPai getPinpaiBehavior() {
        return pinpaiBehavior;
    }

    public void setPinpaiBehavior(PinPai pinpaiBehavior) {
        this.pinpaiBehavior = pinpaiBehavior;
    }

    public Xitong getXitongBehavior() {
        return xitongBehavior;
    }

    public void setXitongBehavior(Xitong xitongBehavior) {
        this.xitongBehavior = xitongBehavior;
    }
}
