package 数据结构.Map;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HashMap8  https://blog.csdn.net/v123411739/article/details/78996181
 * https://blog.csdn.net/v123411739/article/details/106324537?spm=1001.2014.3001.5502
 * 1.7和1.8的区别
 *
 *
 * @Description :
 * 为什么要1.8要改成数组+链表+红黑树?? 答: hash冲突严重时（链表过长）的查找性能，使用链表的查找性能是 O(n)，而使用红黑树是 O(logn)。
 * 什么时候用链表？什么时候用红黑树??
 * 答: 对于插入，默认是使用链表节点。当同一个索引位置的节点在新增后达到9个(阈值8)且此时数组长度大于等于 64，则会触发链表节点转红黑树节点(treeifyBin)
 *     而如果数组长度小于64，则不会触发链表转红黑树，而是会进行扩容，因为此时的数据量还比较小。
 *     对于移除，当同一个索引位置的节点在移除后达到6个，并且该索引位置的节点为红黑树节点，会触发红黑树节点转链表节点（untreeify）。
 *  为什么转回链表节点是用的6而不是复用8？
 *  如果我们设置节点多于8个转红黑树，少于8个就马上转链表，当节点个数在8徘徊时，就会频繁进行红黑树和链表的转换，造成性能的损耗
 *
 * @Author agan
 * @Date 2021/3/29 21:59
 **/

public class HashMap8 {
    private static Map<String, String> map = new HashMap<>();
}
