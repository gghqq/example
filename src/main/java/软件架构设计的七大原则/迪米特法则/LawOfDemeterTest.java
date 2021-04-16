package 软件架构设计的七大原则.迪米特法则;

/**
 * @ClassName LawOfDemeterTest
 * @Description
 * ex:  模拟BOSS让lead统计课程数量
 *
 * 迪米特原则（Law of Demeter LoD）是指一个对象应该对其他对象保持最少的了解，又叫最少知道原则（Least Knowledge Principle,LKP），尽量降低类与类之间的耦合。
 * 迪米特原则主要强调只和朋友交流，不和陌生人说话。出现在成员变量、方法的输入、输出参数中的类都可以称之为成员朋友类，而出现在方法体内部的类不属于朋友类。

 **/

public class LawOfDemeterTest {
    public static void main(String[] args) {
//        这当中的问题是BOSS与Course和 leader和Course都发生了交互
//        根据迪米特原则，Boss只想要结果，不需要跟 Course 产生直接的交流。
//        而 TeamLeader 统计需要引用 Course对象。Boss 和 Course 并不是朋友，
        Boss boss = new Boss();
        boss.commandCheckNum(new TeamLeader());
//        优化后
        boss.commandCheck(new TeamLeader());
    }
}
