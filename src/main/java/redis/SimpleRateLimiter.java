package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

/**
 * @ClassName SimpleRateLimiter
 * @Description : redis 简单滑动窗口限流
 * @Author wpj
 * @Date 2021/10/13 18:10
 **/

public class SimpleRateLimiter {
    private Jedis jedis;
    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     *
     * @param userId   用户标识
     * @param actionKey 操作关键字
     * @param period  时间段,这里 *1000 为秒
     * @param maxCount 最大操作数
     * @return
     */
    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws IOException {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        Pipeline pipe = jedis.pipelined();
//        开启事务
        pipe.multi();
//       添加用户行为
        pipe.zadd(key, nowTs, "" + nowTs);
//        删除之前时间窗口记录的用户行为
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
//        获取窗口内的行为总数
        Response<Long> count = pipe.zcard(key);
//        设置 zset 过期时间，避免冷用户持续占用内存
//        过期时间应该等于时间窗口的长度，再多宽限 1s
        pipe.expire(key, period + 1);
//        提交事务,批量执行
        pipe.exec();
        pipe.close();
//        比较数量是否超标
        return count.get() <= maxCount;
    }

    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis();
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for(int i=0;i<20;i++) {
            System.out.println(limiter.isActionAllowed("laoqian", "reply", 60, 5));
        }
    }
}
