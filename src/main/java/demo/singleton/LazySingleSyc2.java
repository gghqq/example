package demo.singleton;

/**
 * @ClassName LazySingleSyc2
 * @Description   双重校验锁 线程安全
 *
 * @Author agan
 * @Date 2021/4/19 11:45
 **/

public class LazySingleSyc2 {
    private LazySingleSyc2(){};
    private volatile static LazySingleSyc2 lazySingleSyc2 = null ;
    public  static LazySingleSyc2 getInstance(){
        if (lazySingleSyc2 == null){
            synchronized (LazySingleSyc2.class){ //当A线程执行完毕后释放锁还在初始化
                if(lazySingleSyc2 == null){ //B线程进入判定,,A还在初始化. 导致多次实例化对象.  所以要加关键字volatile
                    lazySingleSyc2 = new LazySingleSyc2();
                }
            }
        }
        return lazySingleSyc2;
    }
}
