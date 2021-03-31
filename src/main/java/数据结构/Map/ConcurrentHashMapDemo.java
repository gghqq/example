package 数据结构.Map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentHashMap  https://juejin.cn/post/6936058107973861413
 * @Description
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




//     initTable初始化方法,循环判断,根据sizeCtl判断是不是正在初始化,是的话让出线程,让自己或其他线程执行.
//     cas操作，将-1赋值给sizeCtl,判断有没有初始化完成,然后初始化数组,并记录下一次要调整的阈值
//     helpTransfer帮助扩容.使用cas形成自旋锁进行扩容.
//     transfer采用高低位算法扩容,新数组长度为之前的2倍  任意一个数&2的n次幂要么等于0要么等于2的n次幂。当等于0的数刚好通过 i = (n - 1)& hash计算出的下标刚好是扩容前的下标
}
