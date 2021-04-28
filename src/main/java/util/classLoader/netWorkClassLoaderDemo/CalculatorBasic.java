package util.classLoader.netWorkClassLoaderDemo;

/**
 * @ClassName A
 * @Description
 * @Author agan
 * @Date 2021/4/28 23:17
 **/

public class CalculatorBasic implements ICalculator {

    @Override
    public String calculate(String expression) {
        return expression;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
