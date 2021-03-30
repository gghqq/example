package 数据结构.Map;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HashDemo https://blog.csdn.net/woshimaxiao1/article/details/83661464
 * @Description :Hash表,通过某种hash方法将关键字的映射放在数组的某个位置
 * HashMap 1.7 采用数组+链表的方式
 * 扩容时通过resize先判断大小是否超过阈值,然后将当前容量*2取2^30次方的最小值,然后通过transfer把老的数组放到新的里面.并重新计算hash
 *https://blog.csdn.net/gongsenlin341/article/details/112582217  HashMap链表头插法导致死循环
 *
 *
 * @Author agan
 * @Date 2021/3/26 21:59
 **/

public class HashMap7{
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 实际存储的key-value键值对的个数
     */
    transient int size;

    /**
     * 阈值，当table == {}时，该值为初始容量（初始容量默认为16）；当table被填充了，也就是为table分配内存空间后，
     * threshold一般为 capacity*loadFactory。HashMap在进行扩容时需要参考threshold，后面会详细谈到
     */
    int threshold;

    /**
     * 负载因子，代表了table的填充度有多少，默认是0.75
     * 加载因子存在的原因，还是因为减缓哈希冲突，如果初始桶为16，等到满16个元素才扩容，某些桶里可能就有不止一个元素了。
     * 所以加载因子默认为0.75，也就是说大小为16的HashMap，到了第13个元素，就会扩容成32。
     */
    final float loadFactor = 0.75f;

    //从1.7源码可以看到HashMap内部是一个Entry数组,entry是HashMap内部类,entry有key,value,hash,和entry四个属性
    //Entry<K,V> next;//存储指向下一个Entry的引用，单链表结构, int hash;//对key的hashcode值进行hash运算后得到的值，存储在Entry，避免重复计算
//    在1.7中 HashMap是有数组+链表构成的.数组是主体,链表则主要为了解决哈希冲突而存在.如果通过hash定位等到的数组位置不含链表(当前entry的next指向null)
//    那么查找,添加等操作很快,.如果定位导的数组包含链表,首先遍历链表,存在就覆盖,否则新增,其时间复杂度为O(n),对于查找操作来讲，仍需遍历链表，然后通过key对象的equals方法逐一比对查找。
//    put时,如果HashMap为空数组,就进行扩容,
//      roundUpToPowerOf2(toSize);//capacity一定是2的次幂,取capacity*loadFactor和MAXIMUM_CAPACITY+1的最小值中取最小,
//    .如果key为null,就放入Entry数组的0位或0位链上,  不为null就对key进行hash,根据hash方法可知,实际是对key的hashCode进行计算,在根据hash值找到key在Entry数组的位置.有就覆盖
//      addEntry方法会先判断数组大小有没有超过阈值,超过就按照二倍扩容(并比较2^30取最小),并调用transfer方法把旧的数组移到新的数组上,所以扩容相对来说是个耗资源的
//      在transfer方法中会遍历数组中的链表,并重新计算索引的位置,把旧的数组移到新的数组上

//    HashMap的数组长度一定保持2的次幂，比如16的二进制表示为 10000，那么length-1就是15，二进制为01111，同理扩容后的数组长度为32，
//    二进制表示为100000，length-1为31，二进制表示为011111,从下图可以我们也能看到这样会保证低位全为1，而扩容后只有一位差异，也就是多出了最左位的1，
//    这样在通过 h&(length-1)的时候，只要h对应的最左边的那一个差异位为0，就能保证得到的新的数组索引和老数组索引一致(大大减少了之前已经散列良好的老数组的数据位置重新调换)。


    public static void main(String[] args) {
        Computer[] c = {new Computer("8G", "intel"), new Computer("4G", "AMD")};
        for (Computer cc = c[0]; cc != null; cc = cc.xuniji) {
            System.out.println(cc.toString());
        }
    }
}

class Computer {

    String neicun;
    String cpu;

    @Override
    public String toString() {
        return "Computer{" +
                "neicun='" + neicun + '\'' +
                ", cpu='" + cpu + '\'' +
                ", xuniji=" + xuniji +
                '}';
    }

    public Computer(String neicun, String cpu) {
        this.neicun = neicun;
        this.cpu = cpu;
    }

    Computer xuniji; //虚拟机
}