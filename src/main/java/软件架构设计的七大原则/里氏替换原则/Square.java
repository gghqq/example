package 软件架构设计的七大原则.里氏替换原则;

/**
 * @ClassName Square
 * @Description 正方形 继承长方形父类
 * @Author agan
 * @Date 2021/4/18 22:33
 **/

public class Square implements Quadrangle {
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private int length;


    @Override
    public int getHeight() {
       return length;
    }

    @Override
    public int getWidth() {
        return length;
    }
}
