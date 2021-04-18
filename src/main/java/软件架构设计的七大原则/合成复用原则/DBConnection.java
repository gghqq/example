package 软件架构设计的七大原则.合成复用原则;

/**
 * @ClassName DBConnection
 * @Description
 * @Author agan
 * @Date 2021/4/18 23:07
 **/

public abstract class DBConnection {
//    假如此时我需要使用oracle  则需要修改所有dao获取数据源代码
//    public String getMysqlConnection(){
//        return "mysql连接";
//    }

    public abstract String getConnection();

}
