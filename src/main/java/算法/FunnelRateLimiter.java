package 算法;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FunnelRateLimiter
 * @Description 漏斗算法
 * Funnel 对象的 make_space 方法是漏斗算法的核心，其在每次灌水前都会被调用以触发
 * 漏水，给漏斗腾出空间来。能腾出多少空间取决于过去了多久以及流水的速率。Funnel 对象
 * 占据的空间大小不再和行为的频率成正比，它的空间占用是一个常量
 * @Author wpj
 * @Date 2021/10/14 17:14
 **/

public class FunnelRateLimiter {
    static class Funnel {
        // 容量
        int capacity;
        // 每毫秒漏水的速度
        float leakingRate;
        // 漏斗没有被占满的体积
        int leftQuota;
        // 上次漏水的时间
        long leakingTs;

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            // 初始化为一个空的漏斗
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            // 计算离上次漏斗的时间
            long deltaTs = nowTs - leakingTs;
            // 计算离上次漏斗的时间到现在漏掉的水
            int deltaQuota = (int) (deltaTs * leakingRate);
            if (deltaQuota < 0) { // 间隔时间太长，整数数字过大溢出
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) { // 腾出空间太小，最小单位是 1
                return;
            }
            // 更新腾出的空间
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            // 超出最大限制 复原
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            // 如果腾出的空间大于需要的空间
            if (this.leftQuota >= quota) {
                // 给腾出空间注入流量
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1); // 需要 1 个 quota
    }
}
