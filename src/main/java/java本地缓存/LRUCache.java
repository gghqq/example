package java本地缓存;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName HashMao
 * @Description:    https://juejin.cn/post/6844904199453409294
 *  LinkedHashMap维持了一个链表结构，用来存储节点的插入顺序或者访问顺序（二选一），并且内部封装了一些业务逻辑，只需要覆盖removeEldestEntry方法，便可以实现缓存的LRU淘汰策略。
 *  此外我们利用读写锁，保障缓存的并发安全性。需要注意的是，这个示例并不支持过期时间淘汰的策略。
 * 自实现缓存的方式，优点是实现简单，不需要引入第三方包，比较适合一些简单的业务场景。缺点是如果需要更多的特性，需要定制化开发，成本会比较高，并且稳定性和可靠性也难以保障。
 * 对于比较复杂的场景，建议使用比较稳定的开源工具。
 *
 *
 * @Author wpj
 * @Date 2021/7/29 17:57
 **/

public class LRUCache extends LinkedHashMap {

    /**
     * 可重入读写锁，保证并发读写安全性
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    /**
     * 缓存大小限制
     */
    private int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize + 1, 1.0f, true);
        this.maxSize = maxSize;
    }

    @Override
    public Object get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Object put(Object key, Object value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return this.size() > maxSize;
    }
}


