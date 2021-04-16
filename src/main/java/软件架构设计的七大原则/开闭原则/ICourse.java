package 软件架构设计的七大原则.开闭原则;

import java.math.BigDecimal;

/**
 * 课程接口
 */
public interface ICourse {
    Long getId();
    String getName();
    BigDecimal getPrice();
}
