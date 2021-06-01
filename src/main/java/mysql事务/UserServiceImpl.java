package mysql事务;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserServiceImpl  https://blog.csdn.net/liuziteng0228/article/details/53862614  https://www.cnblogs.com/xrq730/p/5087378.html
 * https://baijiahao.baidu.com/s?id=1629409989970483292
 *
 * @Description:
 * isolation :事务的隔离级别
 *     DEFAULT(-1),  使用底层数据库的默认事务隔离级别 使用的MySQL，可以使用"select @@tx_isolation"来查看默认的事务隔离级别
 *     READ_UNCOMMITTED(1), 允许脏读,幻读,可重复读.
 *     READ_COMMITTED(2),  最常用,防止脏读 ,并且是大多数数据库的默认隔离级别.只能读到已提交事务的数据
 *     REPEATABLE_READ(4), 可以防止脏读和不可重复读 数据读出来之后加锁，类似"select * from XXX for update"，明确数据读取出来就是为了更新用的，所以要加一把锁，防止别人修改它。
 *     SERIALIZABLE(8);     事务串行化,可以防止脏读,不可重复读和幻读. 会降低数据库效率
 *
 *
 *
 * @Author wpj
 * @Date 2021/5/31 21:44
 * @see txt/mysql.txt
 **/

public class UserServiceImpl implements UserService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public int update(User user) {
        return 0;
    }

    @Override
    public User getU(int id) {
        return null;
    }
}
