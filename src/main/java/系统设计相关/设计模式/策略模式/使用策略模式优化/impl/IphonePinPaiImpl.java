package 系统设计相关.设计模式.策略模式.使用策略模式优化.impl;

import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.PinPai;

/**
 * @ClassName IphonePinPaiImpl
 * @Description
 * @Author wpj
 * @Date 2021/10/21 22:28
 **/

public class IphonePinPaiImpl implements PinPai {
    @Override
    public void pinpai() {
        System.out.println("~~苹果品牌");
    }
}
