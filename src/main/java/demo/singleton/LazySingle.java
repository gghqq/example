package demo.singleton;

/**
 * @ClassName lazySingle
 * @Description 懒汉式
 * @Author agan
 * @Date 2021/4/19 11:18
 **/

public class LazySingle {
    private LazySingle(){};
    private static LazySingle lazySingle=null;
    public static LazySingle getInstance(){
        if (lazySingle == null){  //多个线程同时执行到,这将导致实例化多次对象。即此类不是单例了
            lazySingle = new LazySingle();
        }
        return lazySingle;
    }
}
