package JVM.垃圾回收;

/**
 * @ClassName GCAlgorithm  https://www.cnblogs.com/aspirant/p/8662690.html
 * @Description: 垃圾回收判断对象是否存活的算法
 * 1.引用计数法
 * 1.1 堆中每个对象实例都有一个引用计数。当一个对象被创建时，就将该对象实例分配给一个变量，该变量计数设置为1。
 * 当任何其它变量被赋值为这个对象的引用时，计数加1（a = b,则b引用的对象实例的计数器+1），
 * 但当一个对象实例的某个引用超过了生命周期或者被设置为一个新值时，对象实例的引用计数器减1。
 * 任何引用计数器为0的对象实例可以被当作垃圾收集。当一个对象实例被垃圾收集时，它引用的任何对象实例的引用计数器减1。
 * 1.2 优点：引用计数收集器可以很快的执行，交织在程序运行中。对程序需要不被长时间打断的实时环境比较有利。
 * 缺点：无法检测出循环引用。如父对象有一个对子对象的引用，子对象反过来引用父对象。这样，他们的引用计数永远不可能为0。
 * 2.可达性分析算法
 *
 *
 *
 *
 * @Author agan
 * @Date 2021/4/22 20:48
 **/

public class GCAlgorithm {
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            /**
             *  计数算法不能检测出循环引用。最后面两句将object1和object2赋值为null，也就是说object1和object2指向的对象已经不可能再被访问，
             *  但是由于它们互相引用对方，导致它们的引用计数器都不为0，那么垃圾收集器就永远不会回收它们。
             *  存在疑问?  就打印的垃圾回收日志来看.每次老年代空间都基本一致,不知道是回收了还是没有回收
             */
            MyObject object1=new MyObject();
            MyObject object2=new MyObject();
//            object1.object=object2; //第二次测试回收
//            object2.object=object1; //第二次测试回收
//            object1=null;  //第三次测试回收
//            object2=null; //第三次测试回收

            object1=object2; //第四次测试回收
            object2=object1; //第四次测试回收

            System.gc();
        }

}

class MyObject{
        MyObject object;
}
