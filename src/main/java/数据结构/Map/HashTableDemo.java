package 数据结构.Map;

import java.util.Hashtable;

/**
 * @ClassName HashTable
 * @Description
 *
 * 1. Hashtable 是散列表的实现，处理散列冲突使用的是链表法，内部结构可以理解为「数组 + 链表」；
 * 2. 默认初始化容量为 11，默认负载因子为 0.75；
 * 3. 线程安全，使用 synchronized 关键字，并发效率低；
 * 4. 若无需保证线程安全，推荐使用 HashMap；若需要线程安全的高并发场景，推荐使用 ConcurrentHashMap
 *
 * @Author agan
 * @Date 2021/4/19 14:23
 **/

public class HashTableDemo {
    private static Hashtable hashTable = new Hashtable();
}
