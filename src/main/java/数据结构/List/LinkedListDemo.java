package 数据结构.List;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName LinkedList https://juejin.cn/post/6844903566331609095
 * @Description
 *
 *  LinkedList 是基于链表实现的。LinkedList 插入和删除方面要优于 ArrayList 随机访问上则 ArrayList 性能更好
 *  除了 LIst 接口之外，LinkedList 还实现了 Deque，Cloneable，Serializable 三个接口。这说明该数据结构支持队列，克隆和序列化操作的。
 *  与 ArrayList 一样，允许 null 元素的存在，且是不支持多线程的。
 *  从源码可以看出 LinkedList 是基于链表实现的。
 * 在查找和删除某元素时，区分该元素为 null和不为 null 两种情况来处理，LinkedList 中允许元素为 null。
 * 基于链表实现不存在扩容问题。
 * 查找时先判断该节点位于前半部分还是后半部分，加快了速度
 * 因为基于链表，所以插入删除极快，查找比较慢。
 * 实现了栈和队列的相关方法，所以可作为栈，队列，双端队列来用
 *
 * @Author agan
 * @Date 2021/3/25 22:25
 **/

public class LinkedListDemo {
//    LinkedList有两个构造方法,第二个构造方法实际调用了第一个和addAll方法
    public static List<String> linkedList = new LinkedList<>();
    public static List<String> linkedList1 = new LinkedList<>(linkedList);

//    从源码上看LinkedList的三个成员变量size,first,last分别表示链表长度,首节点和尾结点,节点为Node对象.
//    Node为LinkedList的内部类,定义了节点内的元素,上个节点和下个节点,是典型的双链表
        private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}

//    addAll :方法会先检查索引,然后把新增的集合转为数组并检查数组长度==0,然后定义两个节点pred,succ
//    判断插入位置的索引并记录下插入位置的当前节点和上一个节点,然后循环集合并判断插入位置的上一个节点是不是空,
//    如果是null说明从头开始插入,不为空则设置插入位置的下一个节点为自己,自己则是下一个节点的上一个节点
//    add :会调用linkLast.将最后一个节点设置为新节点的上一个节点,并将链表的最后一个节点设置为新节点,
//    然后判断是不是从0插入的链表,如果是将链表的第一个节点设置为新节点,否则将链表的原来最后节点的下一个节点指向新节点
//    add(index,E)会先判断索引,然后连接起来....
//    这里的node(index)获取索引节点方法通过与当前链表大小一半作比较来判断索引在上半段还是在后半段.
      public static void  main (String[] args){
            List<String> s = Lists.newLinkedList();
            s.add("111");
            s.add("222");
            s.add("333");
            s.remove(null);
            s.parallelStream().forEach(System.out::println);
      }
}
