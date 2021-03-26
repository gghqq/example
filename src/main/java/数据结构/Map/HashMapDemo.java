package 数据结构.Map;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HashDemo
 * @Description :Hash表,通过某种hash方法将关键字的映射放在数组的某个位置
 * HashMap 1.7 采用数组+链表的方式
 *
 * @Author agan
 * @Date 2021/3/26 21:59
 **/

public class HashMapDemo {
    private static Map<String,String> map =Maps.newHashMap();
    public static void main(String[] args){
        new HashMap<>(500,0.75f);
    }
}
