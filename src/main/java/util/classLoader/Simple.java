package util.classLoader;

/**
 * @ClassName Simple
 * @Description
 * @Author agan
 * @Date 2021/4/29 21:37
 **/

public class Simple {
    private Simple instance;
    public void setSample(Object instance) {
        System.out.println(instance.toString());
        this.instance = (Simple) instance;
    }
    public void sout(){
        System.out.println("加载该类并获取到了该方法");
    }
}
