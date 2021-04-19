package demo.singleton;

/**
 * @ClassName LazySingleSyc
 * @Description 懒汉式 线程安全
 * synchronized会有线程堵塞，性能上不好，不推荐。
 * @Author agan
 * @Date 2021/4/19 11:22
 **/

public class LazySingleSyc {
    private LazySingleSyc(){};
    private LazySingleSyc lazySingleSyc = null;
    public synchronized LazySingleSyc getInstance(){
        if (lazySingleSyc == null){
            lazySingleSyc = new LazySingleSyc();
        }
        return lazySingleSyc;
    }
}
