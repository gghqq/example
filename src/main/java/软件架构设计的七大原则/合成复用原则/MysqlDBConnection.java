package 软件架构设计的七大原则.合成复用原则;

/**
 * @ClassName MysqlDBConnection
 * @Description
 * @Author agan
 * @Date 2021/4/18 23:15
 **/

public class MysqlDBConnection extends DBConnection {
    @Override
    public String getConnection() {
        return "oracle连接";
    }
}
