package demo.singleton;

/**
 * @ClassName EagerSingle
 * @Description: 饿汉式
 * 单例模式的组成:包括一个私有的构造器，一个私有的静态变量，一个公有的静态方法。
 * 单例模式本身很简单，主要复杂点是在它在线程并发下的如何保证 线程安全+资源消耗少 的问题。
 *
 *  直接实例化，资源会浪费。丢失了延迟实例化的性能好处。
 * @Author agan
 * @Date 2021/4/19 11:15
 **/

public class EagerSingle {
//    私有化构造器
    private EagerSingle(){};
//    静态实例
    private static EagerSingle eagerSingle = new EagerSingle();
//    获取单例 (线程安全)
    public static EagerSingle getInstance(){
        return  eagerSingle;
    }
}
