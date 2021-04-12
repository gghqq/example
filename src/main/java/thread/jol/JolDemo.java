package thread.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @ClassName JolDemo
 * @Description
 * 我们可以认为一个对象的布局大体分为三个部分分别是：对象头(object header)、对象的实例数据、对齐字节(对齐填充)；
 * openjdk中对象头的一些专业术语：http://openjdk.java.net/groups/hotspot/docs/HotSpotGlossary.html
 * 文档指出java对象头包含了2个word，并且包含了堆对象的布局、类型、GC状态、同步状态和标识哈希码.
 * mark word为第一个word根据文档可以知道他里面包含了锁的信息，hashcode,gc信息等等
 * kclass word为第二个word根据文档可以知道这个主要指向对象的元数据
 * hotspot(jvm)的源码注释中得知一个 mark word是一个64bits(源码：Mark Word(64bits))
 * 一个对象头(object header)是12Byte(96bits)，而JVM源码中：Mark Word为8Byte(64bits)，可以得出klass是4Byte(32bits)【jvm默认开启了指针压缩：压缩：4Byte(32bits)；不压缩：8byte(64bits)】
 * 锁相关的就是mark word了，接下来重点分析mark word里面信息
 * 根据hotspot（jvm）的源码注释中得知在无锁的情况下mark word当中的前56bits存的是对象的hashcode（unused:25 + hash:31 = 56 bits--> hashcode）；
 * @Author agan
 * @Date 2021/4/12 15:56
 **/

public class JolDemo {
//    static ClassDemo1 classDemo = new ClassDemo1();
    static ClassDemo2 classDemo = new ClassDemo2();
    public static void main(String[] args){
//        System.out.println(VM.current().details());
        System.out.println("没有计算HASHCODE之前的对象头");
        System.out.println(ClassLayout.parseInstance(classDemo).toPrintable());
        System.out.println("//计算完hashcode 转为16进制：");
        System.out.println("jvm hashcode------------0x"+Integer.toHexString(classDemo.hashCode()));
        //当计算完hashcode之后，我们可以查看对象头的信息变化
        System.out.println("after hash");
        System.out.println(ClassLayout.parseInstance(classDemo).toPrintable());//无锁的情况下mark word当中的前56bits存的是对象的hashcode

    }

}
//运行结果得出一个空的对象为16Byte，其中对象头(object header)占12Byte，剩下的为对齐字节占4Byte(也叫对齐填充，jvm规定对象头部分必须是 8 字节的倍数);
// 由于这个对象没有任何字段，所以之前说的对象实例是没有的(0 Byte);
class ClassDemo1 {
}
//整个对象的大小没有改变还是一共16Byte，其中对象头 (object header)占12Byte，boolean 字段 DemoTest.flag(对象的实例数据)占1Byte，剩下的3Byte为对齐子节(对齐填充)；
class ClassDemo2 {
    //占1byte的boolean
    boolean flag = false;
}