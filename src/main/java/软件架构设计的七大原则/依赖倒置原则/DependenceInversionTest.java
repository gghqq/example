package 软件架构设计的七大原则.依赖倒置原则;

/**
 * @ClassName DependenceInversionTest
 * @Description
 * 依赖倒置原则（Dependence Inversion Principle,DIP）是指设计代码结构时，高层模块不应该依赖底层模块，二者都应该依赖其抽象。
 * 抽象不应该依赖细节；细节应该依赖抽象。通过依赖倒置，可以减少类与类之间的耦合性，提高系统的稳定性，提高代码的可读性和可维护性，并能够降低修改程序所造成的风险。
 *
 * @Author agan
 * @Date 2021/4/15 20:21
 **/

public class DependenceInversionTest {
    public static void main(String[] args) {
//        Tom 目前正在学习 Java 课程和 Python 课程。现在 Tom 还想学习 AI 人工智能的课程。这个时候，业务扩展，我们的代码要从底层到高层（调用层）一次修改代码。
//        在 Tom 类中增加studyAICourse()的方法，在高层也要追加调用。
        final Tom tom = new Tom();
        tom.studyPython();
        tom.studyJava();
//        优化 创建一个课程抽象 ，对于新的课程，我只需要新建一个类，通过传参的方式告诉 Tom，而不需要修改底层代码。
//        实际上这是一种大家非常熟悉的方式，叫依赖注入。
//       构造器注入方式： 通过构造器注入,  set方法注入来实现
        final Tom tom1 = new Tom(new JavaCourse());
        tom1.study();
        tom.setICourse(new PythonCourse());
        tom.study();

    }
}
