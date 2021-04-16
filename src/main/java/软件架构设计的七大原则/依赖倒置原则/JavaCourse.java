package 软件架构设计的七大原则.依赖倒置原则;

/**
 * @ClassName JavaCourse
 * @Description
 * @Author agan
 * @Date 2021/4/15 20:29
 **/

public class JavaCourse implements ICourse {
    @Override
    public void study() {
        System.out.println("学习JAVA");
    }
}
