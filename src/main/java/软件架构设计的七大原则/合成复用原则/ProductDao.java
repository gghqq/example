package 软件架构设计的七大原则.合成复用原则;

/**
 * @ClassName ProductDao
 * @Description:
 * 合成复用原则（Composite/Aggregate Reuse Principle,CARP）是指尽量使用对象组合(has-a)/聚合(contanis-a)，而不是继承关系达到软件复用的目的。
 * 可以使系统更加灵活，降低类与类之间的耦合度，一个类的变化对其他类造成的影响相对较少.
 * 继承我们叫做白箱复用，相当于把所有的实现细节暴露给子类。组合/聚合也称之为黑箱复用，对类以外的对象是无法获取到实现细节的。
 * 要根据具体的业务场景来做代码设计，其实也都需要遵循 OOP 模型。
 * @Author agan
 * @Date 2021/4/18 23:08
 **/

public class ProductDao {
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private DBConnection dbConnection;

    public void addProduct(){
//        假如此时我需要使用oracle  则需要修改所有dao获取数据源代码
//        String mysqlConnection = dbConnection.getMysqlConnection();
        String connection = dbConnection.getConnection();
        System.out.println("使用:"+connection+"增加商品");

    }

}
