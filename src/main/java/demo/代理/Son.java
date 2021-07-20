package demo.代理;

/**
 * @ClassName Son
 * @Description
 * @Author wpj
 * @Date 2021/7/8 16:18
 **/

public class Son  extends Father{


    @Override
    public void fmethod(String str) throws Exception {
        System.out.println("子类继承父类方法并实现");
        sout(str);
    }


    public static void main(String[] args) {
        Son son = new Son();
        try {
            son.fmethod("子类调用父类方法并实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
