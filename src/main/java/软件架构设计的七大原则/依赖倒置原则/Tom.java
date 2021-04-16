package 软件架构设计的七大原则.依赖倒置原则;

/**
 * @ClassName Tom
 * @Description
 * @Author agan
 * @Date 2021/4/15 20:17
 **/

public class Tom {


    private ICourse iCourse;

//    这是方法参数注入
//    public void study(ICourse iCourse){
//       iCourse.study();
//    }
//    set方法注入
    public void setICourse(ICourse iCourse) {
        this.iCourse = iCourse;
    }

//    构造器注入
    public Tom(ICourse iCourse) {
        this.iCourse = iCourse;
    }

    public void study(){
        iCourse.study();
    }



    public Tom() {
    }
    public void studyJava(){
        System.out.println("tom学习Java");
    }

    public void studyPython(){
        System.out.println("tom学习Python");
    }

}
