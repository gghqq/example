package demo.singleton;

/**
 * @ClassName LasySingleSyc3
 * @Description  静态内部类实现。具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持。
 * 内部静态类的变量并不会在类加载的时候就初始化好，而是会在调用的时候才初始化。保证资源的节约。
 *
 * @Author agan
 * @Date 2021/4/19 13:37
 **/

public class LazySingleSyc3 {
    private static class instance{
        private static final   LazySingleSyc3 lazySingleSyc3 = new LazySingleSyc3();
    }
    private LazySingleSyc3(){};

    public static LazySingleSyc3 getInstance(){
        return instance.lazySingleSyc3;
    }
}
