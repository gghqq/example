package 软件架构设计的七大原则.开闭原则;

import java.math.BigDecimal;

/**
 * @ClassName JavaDiscountCourse
 * @Description
 * @Author agan
 * @Date 2021/4/15 17:22
 **/

public class JavaDiscountCourse extends JavaCourse {

    public JavaDiscountCourse(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

//    伏笔 此处违反里氏替换原则 不应该覆盖父类的非抽象方法
    public BigDecimal getOrigin(){
       return super.getPrice();
    }

    public BigDecimal getPrice(){
        return super.getPrice().divide(new BigDecimal(10));
    }


}
