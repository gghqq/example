package util.classLoader.netWorkClassLoaderDemo;

/**
 * @ClassName ICalculator
 * @Description
 * @Author agan
 * @Date 2021/4/28 23:16
 **/

public interface   ICalculator extends Versioned{
    String calculate(String expression);
}
