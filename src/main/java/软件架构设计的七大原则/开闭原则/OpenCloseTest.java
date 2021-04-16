package 软件架构设计的七大原则.开闭原则;

import java.math.BigDecimal;

/**
 * @ClassName OpenCloseTest
 * @Description
 *
 * 开闭原则（Open-Closed Principle, OCP）是指一个软件实体如类、模块和函数应该对扩展开放，对修改关闭。所谓的开闭，也正是对扩展和修改两个行为的一个原则。
 * 强调的是用抽象构建框架，用实现扩展细节。可以提高软件系统的可复用性及可维护性。
 * 开闭原则，是面向对象设计中最基础的设计原则。它指导我们如何建立稳定灵活的系统，例如：我们版本更新，我尽可能不修改源代码，但是可以增加新功能。
 * 在现实生活中对于开闭原则也有体现。比如，很多互联网公司都实行弹性制作息时间，规定每天工作 8 小时。意思就是说，对于每天工作 8 小时这个规定是关闭的，但是你什么时候来，什么时候走是开放的。早来早走，晚来晚走。
 *
 *
 * @Author agan
 * @Date 2021/4/15 17:26
 **/

public class OpenCloseTest {
    public static void main(String[] args){
       ICourse jdc = new JavaDiscountCourse(1L, "多线程", new BigDecimal(5000));
        System.out.println(jdc.getPrice());
    }

}
