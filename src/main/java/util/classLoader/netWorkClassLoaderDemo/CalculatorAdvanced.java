package util.classLoader.netWorkClassLoaderDemo;

/**
 * @ClassName B
 * @Description
 * @Author agan
 * @Date 2021/4/28 23:18
 **/

public class CalculatorAdvanced implements ICalculator {

    @Override
    public String calculate(String expression) {
        return "2.0 Result is " + expression;
    }

    @Override
    public String getVersion() {
        return "2.0";
    }
}
