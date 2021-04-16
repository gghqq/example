package 软件架构设计的七大原则.开闭原则;

import java.math.BigDecimal;

/**
 * @ClassName JavaCourse
 * @Description 现在有一个java课程.
 * 我们要给 Java 架构课程做活动，价格优惠。如果修改 JavaCourse 中的 getPrice()方法，
 * 则会存在一定的风险，可能影响其他地方的调用结果。我们如何在不修改原有代码前提前下，实现价格优惠这个功能呢？现在，我们再写一个处理优惠逻辑的类，
 * @Author agan
 * @Date 2021/4/15 17:18
 **/

public class JavaCourse implements ICourse {

    private Long id;
    private String name;
    private BigDecimal price;

    public JavaCourse(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }
}
