package 软件架构设计的七大原则.单一职责原则;

/**
 * @ClassName ICourse
 * @Description
 * 对于控制课程层面上至少有两个职责。我们可以把展示职责和管理职责分离开来，都实现同一个抽象依赖。
 * 设计一个顶层接口,创建ICourse
 * 这里对单一职责体现的明不是特别明显,
 * 我认为单一职责就是把类或者方法的任务区分清楚
 * @Author agan
 * @Date 2021/4/15 21:41
 **/

public interface ICourse {
    //获得基本信息
    String getCourseName();
    //获得视频流
    byte[] getCourseVideo();

    //学习课程
    void studyCourse();
    //退款
    void refundCourse();
}

interface ICourseInfo{
    //获得基本信息
    String getCourseName();
    //获得视频流
    byte[] getCourseVideo();
}
interface ICourseManager{
    //学习课程
    void studyCourse();
    //退款
    void refundCourse();
}