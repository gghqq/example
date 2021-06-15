package util.hash;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * @ClassName ConsistentHashingUtile https://kefeng.wang
 * @Description
 *
 *
 * @Author wpj
 * @Date 2021/6/3 11:19
 **/

public class ConsistentHashingUtil {
    // 物理节点
    private Set<String> physicalNodes = Sets.newTreeSet(
            new ArrayList<String>(){{
                add("192.168.1.101");
                add("192.168.1.102");
                add("192.168.1.103");
                add("192.168.1.104");
            }}
    );

    //虚拟节点
    private final int VIRTUAL_COPIES = 1048576; // 物理节点至虚拟节点的复制倍数
    private TreeMap<Long, String> virtualNodes = Maps.newTreeMap(); // 哈希值 => 物理节点


    // 根据物理节点，构建虚拟节点映射表
    public ConsistentHashingUtil() {
        for (String nodeIp : physicalNodes) {
            addPhysicalNode(nodeIp);
        }
    }

    // 添加物理节点,
    public void addPhysicalNode(String nodeIp) {
        for (int idx = 0; idx < VIRTUAL_COPIES; ++idx) {
            long hash = FNVHash(nodeIp + "#" + idx);
            virtualNodes.put(hash, nodeIp);
        }
    }

    // 删除物理节点
    public void removePhysicalNode(String nodeIp) {
        for (int idx = 0; idx < VIRTUAL_COPIES; ++idx) {
            long hash = FNVHash(nodeIp + "#" + idx);
            virtualNodes.remove(hash);
        }
    }


    // 查找对象映射的节点
    public String getObjectNode(String object) {
        long hash = FNVHash(object);
        SortedMap<Long, String> tailMap = virtualNodes.tailMap(hash); // 所有大于 hash 的节点
        Long key = tailMap.isEmpty() ? virtualNodes.firstKey() : tailMap.firstKey();
        return virtualNodes.get(key);
    }

    // 统计对象与节点的映射关系
    public void dumpObjectNodeMap(String label, int objectMin, int objectMax) {
        // 统计
        Map<String, Integer> objectNodeMap = new TreeMap<>(); // IP => COUNT
        for (int object = objectMin; object <= objectMax; ++object) {
            String nodeIp = getObjectNode(Integer.toString(object));
            Integer count = objectNodeMap.get(nodeIp);
            objectNodeMap.put(nodeIp, (count == null ? 0 : count + 1));
        }

        // 打印
        double totalCount = objectMax - objectMin + 1;
        System.out.println("======== " + label + " ========");
        for (Map.Entry<String, Integer> entry : objectNodeMap.entrySet()) {
            long percent = (int) (100 * entry.getValue() / totalCount);
            System.out.println("IP=" + entry.getKey() + ": RATE=" + percent + "%");
        }
    }

    public static void main(String[] args) {
        ConsistentHashingUtil ch = new ConsistentHashingUtil();
//
//        // 初始情况
//        ch.dumpObjectNodeMap("初始情况", 0, 65536);
//
//        // 删除物理节点
//        ch.removePhysicalNode("192.168.1.103");
//        ch.dumpObjectNodeMap("删除物理节点", 0, 65536);
//
//        // 添加物理节点
//        ch.addPhysicalNode("192.168.1.108");
//        ch.dumpObjectNodeMap("添加物理节点", 0, 65536);

        String[] keys = {"太阳", "月亮", "星星"};
        for (int i = 0; i < keys.length; i++){
            System.out.println("[" + keys[i] + "]的hash值为" +
                    FNVHash(keys[i]) + ", 被路由到结点[" + ch.getObjectNode(keys[i]) + "]");
        }


    }



    // 32位的 Fowler-Noll-Vo 哈希算法
    // https://en.wikipedia.org/wiki/Fowler–Noll–Vo_hash_function
    private static Long FNVHash(String key) {
        final int p = 16777619;
        Long hash = 2166136261L;
        for (int idx = 0, num = key.length(); idx < num; ++idx) {
            hash = (hash ^ key.charAt(idx)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }






}
