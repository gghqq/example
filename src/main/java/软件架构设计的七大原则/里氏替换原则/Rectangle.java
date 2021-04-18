package 软件架构设计的七大原则.里氏替换原则;

/**
 * @ClassName Rectangle
 * @Description  长方形父类
 * 优化后修改长方形继承四边形Quadrangle
 * @Author agan
 * @Date 2021/4/18 22:29
 **/

public class Rectangle implements  Quadrangle {
    private int height;

    private int width;

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
