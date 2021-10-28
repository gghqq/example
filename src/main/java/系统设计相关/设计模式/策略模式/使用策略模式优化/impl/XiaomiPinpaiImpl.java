package 系统设计相关.设计模式.策略模式.使用策略模式优化.impl;


import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.PinPai;

/**
 * @ClassName PinpaiImpl
 * @Description
 * @Author wpj
 * @Date 2021/10/21 21:42
 **/

public class XiaomiPinpaiImpl implements PinPai {
    @Override
    public void pinpai() {
        System.out.println("~~~小米品牌");
    }
}
