package 系统设计相关.设计模式.策略模式.使用策略模式优化;

import 系统设计相关.设计模式.策略模式.使用策略模式优化.impl.AnzhuoXitongImpl;
import 系统设计相关.设计模式.策略模式.使用策略模式优化.impl.IphonePinPaiImpl;

/**
 * @ClassName Iphone
 * @Description
 * @Author wpj
 * @Date 2021/10/21 22:21
 **/

public class Iphone extends Phone {

    public Iphone(){
        pinpaiBehavior = new IphonePinPaiImpl();
        xitongBehavior = new AnzhuoXitongImpl();
    }

    @Override
    public void takePhoto() {

    }
}
