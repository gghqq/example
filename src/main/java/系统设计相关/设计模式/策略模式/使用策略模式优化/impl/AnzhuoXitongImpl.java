package 系统设计相关.设计模式.策略模式.使用策略模式优化.impl;


import 系统设计相关.设计模式.策略模式.使用策略模式优化.inter.Xitong;

/**
 * @ClassName Xitong
 * @Description
 * @Author wpj
 * @Date 2021/10/21 21:42
 **/

public class AnzhuoXitongImpl implements Xitong {
    @Override
    public void xitong() {
        System.out.println("~~~安卓系统");
    }
}
