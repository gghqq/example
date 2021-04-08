package 数据结构.Map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentHashMap  https://juejin.cn/post/6936058107973861413
 * @Description
 * 在JDK1.7中ConcurrentHashMap采用了数组+Segment+分段锁的方式实现。
 * ConcurrentHashMap中的分段锁称为Segment，它即类似于HashMap的结构，即内部拥有一个Entry数组，
 * 数组中的每个元素又是一个链表,同时又是一个ReentrantLock（Segment继承了ReentrantLock）。
 * ConcurrentHashMap定位一个元素的过程需要进行两次Hash操作。第一次Hash定位到Segment，第二次Hash定位到元素所在的链表的头部。
 * 这种结构的带来的副作用是Hash的过程要比普通的HashMap要长。
 * 好处是写操作的时候可以只对元素所在的Segment进行加锁即可，不会影响到其他的Segment，在最理想的情况下，ConcurrentHashMap可以最高同时支持Segment数量大小的写操作（刚好这些写操作都非常平均地分布在所有的Segment上）。
 * 所以，通过这一种结构，ConcurrentHashMap的并发能力可以大大的提高。
 *
 * jdk1.8ConcurrentHashMap是基于cas和synchronized实现线程安全 内部大量采用CAS操作
 *
 * @Author agan
 * @Date 2021/3/29 22:25
 **/

public class ConcurrentHashMapDemo {
     Map<String,String> map = new ConcurrentHashMap<>();

     //最大容量 2的30次幂
     private static final int MAXIMUM_CAPACITY = 1 << 30;
     //默认初始容量
     private static final int DEFAULT_CAPACITY = 16;
     //数组的最大容量，主要是toArray()方法使用
     static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
     //并发数，jdk1.8不使用
     private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
     //加载因子
     private static final float LOAD_FACTOR = 0.75f;
     //当链表中节点数大于8时转红黑树
     static final int TREEIFY_THRESHOLD = 8;
     //当红黑树节点数小于6时转成链表
     static final int UNTREEIFY_THRESHOLD = 6;
     //节点的hash属性为MOVED时，表示map正在扩容
     static final int MOVED     = -1;
     //为-2, 代表此元素后接红黑树
     static final int TREEBIN   = -2;
     static final int RESERVED  = -3;
     //主要用来计算Hash值的
     static final int HASH_BITS = 0x7fffffff;
// 这是扩容时的标记节点，主要在扩容时使用
//     ForwardingNode
     //容器的计数器，没有竞争时可以表示容器中元素长度
     private transient volatile long baseCount;
     /**
      * -1 时表示正在初始化,默认为0,初始化后，保留下一个要调整表大小的元素计数值。
      * tab.length*LOAD_FACTOR(因子 0.75) 时表示初始化完成
      **/
     private transient volatile int sizeCtl;
//     只有大于64且链表长度大于8时,链表才会转为红黑树
     static final int MIN_TREEIFY_CAPACITY = 64;


//    put方法.对k,v判空并抛出异常.对k.hashCode进行hash,循环,并判断需不需要初始化数组,然后查看放入的节点有没有值,cas操作,创建一个新的node节点,并放入k,v.
///    (fh = f.hash) == MOVED 如果在扩容则协助扩容.
//     然后对 链表头或者根节点上锁,校验刚刚拿到的node与现有是否一致,一致就kv加载链表后.
//     或者把kv放入红黑树中.  kv添加完成后最后判断是否需要转红黑树.最后统计数量,并校验是否需要扩容

//     final V putVal(K key, V value, boolean onlyIfAbsent) {
//          //对key和value做校验
//          if (key == null || value == null) throw new NullPointerException();
//          int hash = spread(key.hashCode());
//          int binCount = 0;
//          for (Node<K,V>[] tab = table;;) {//死循环
//               // f是头节点
//               Node<K,V> f; int n, i, fh;
//               if (tab == null || (n = tab.length) == 0)//如果数组没有初始化，则初始化数组
//                    tab = initTable();
//               else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {//获取当前tab中i下标的node，其实是链表或红黑树头节点
//                    //cas操作，如果当前tab在i下标上的头节点为null，创建新的node添加到i下标处，成功后跳出死循环，不成功继续下一轮循环
//                    if (casTabAt(tab, i, null,new Node<K,V>(hash, key, value, null)))
//                         break;
//               }
//               else if ((fh = f.hash) == MOVED)//帮助扩容
//                    tab = helpTransfer(tab, f);
//               else {
//                    //链表的处理
//                    V oldVal = null;
//                    //为f即头节点上锁
//                    synchronized (f) {
//                         //再次校验最新获取的node是否与f相等，如果不相等再次进入循环，如果相等则进行下步执行
//                         if (tabAt(tab, i) == f) {
//                              if (fh >= 0) {
//                                   binCount = 1;
//                                   for (Node<K,V> e = f;; ++binCount) {
//                                        K ek;
//                                        if (e.hash == hash &&
//                                                ((ek = e.key) == key ||
//                                                        (ek != null && key.equals(ek)))) {
//                                             oldVal = e.val;
//                                             if (!onlyIfAbsent)
//                                                  e.val = value;
//                                             break;
//                                        }
//                                        //先将e赋值给pred，然后e=e.next;一直循环到当前的链表的最后一个节点，然后将新的key value创建新的node，追加到当前链表的最后。
//                                        Node<K,V> pred = e;
//                                        if ((e = e.next) == null) {
//                                             pred.next = new Node<K,V>(hash, key,
//                                                     value, null);
//                                             break;
//                                        }
//                                   }
//                              }else if (f instanceof TreeBin) {//红黑树操作
//                                   Node<K,V> p;
//                                   binCount = 2;
//                                   if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
//                                           value)) != null) {
//                                        oldVal = p.val;
//                                        if (!onlyIfAbsent)
//                                             p.val = value;
//                                   }
//                              }
//                         }
//                    }
//                    //表示当前key value添加成功了
//                    if (binCount != 0) {
//                         //判断是否需要转红黑树
//                         if (binCount >= TREEIFY_THRESHOLD)
//                              treeifyBin(tab, i);
//                         if (oldVal != null)
//                              return oldVal;
//                         break;
//                    }
//               }
//          }
//          //元素添加完成后，统计数量，并且校验是否需要扩容
//          addCount(1L, binCount);
//          return null;
//     }



//     initTable初始化方法,循环判断,根据sizeCtl判断是不是正在初始化,是的话让出线程,让自己或其他线程执行.
//     cas操作，将-1赋值给sizeCtl,判断有没有初始化完成,然后初始化数组,并记录下一次要调整的阈值
//     helpTransfer帮助扩容.使用cas形成自旋锁进行扩容.
//     transfer采用高低位算法扩容,新数组长度为之前的2倍  任意一个数&2的n次幂要么等于0要么等于2的n次幂。当等于0的数刚好通过 i = (n - 1)& hash计算出的下标刚好是扩容前的下标
}
