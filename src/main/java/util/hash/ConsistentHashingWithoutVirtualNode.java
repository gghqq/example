package util.hash;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @ClassName ConsistentHashingWithoutVirtualNode   https://blog.csdn.net/suifeng629/article/details/81567777
 * @Description 一致性Hash
 * 将节点服务器和数据进行hash计算,分配到 2^32-1 的环上,根据数据的hash值在环上的位置存放在环上的下一个节点
 * 当增加节点或者减少节点时,只有指向该环的数据需要计算.其他数据则可以不用处理.
 * 问题:当三个服务器的位置接近时,可能会发生hash倾斜,导致所有数据在一个节点上.可通过实际节点生成虚拟节点来解决
 * <p>
 * <p>
 * <p>
 * 不带虚拟节点的一致性hash算法.
 * @Author wpj
 * @Date 2021/6/2 22:34
 **/

public class ConsistentHashingWithoutVirtualNode {

    //    待加入hash环的服务器列表
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111",
            "192.168.0.2:111", "192.168.0.3:111", "192.168.0.4:111"};

    //    TreeMap实现了SortedMap接口，保证了有序性。默认的排序是根据key值进行升序排序
    //    key表示服务器的hash值,value表示服务器
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    //程序初始化，将所有的服务器放入sortedMap中
    static {
        for (String s : servers) {
            int hash = getHash(s);
            System.out.println("[" + s + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, s);
        }
    }


    private static String getServer(String key) {
//      得到该值的hash值
        int hash = getHash(key);
//      得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash); //返回所有大于该值的map
        if (subMap.isEmpty()) {
//         如果没有比该key的hash值大的,则从第一个node开始
            Integer i = sortedMap.firstKey(); //firstKey返回map中最小的key
            //返回对应的服务器
            return sortedMap.get(i);
        }
//        第一个key就是顺时针过去最近的那个节点
        Integer i = subMap.firstKey();
        return subMap.get(i);
    }


    //使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        String[] keys = {"太阳", "月亮", "星星"};
        for (String key : keys) {
            System.out.println("[" + key + "]的hash值为" + getHash(key)
                    + ", 被路由到结点[" + getServer(key) + "]");
        }
    }
}
