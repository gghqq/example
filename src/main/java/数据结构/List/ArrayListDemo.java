package 数据结构.List;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName ArrayList  https://blog.csdn.net/sihai12345/article/details/79382649
 * @Description
 * ArrayList 是 java 集合框架中比较常用的数据结构了。继承自 AbstractList，实现了 List 接口。底层基于数组实现容量大小动态变化。
 * 允许 null 的存在。同时还实现了 RandomAccess、Cloneable、Serializable 接口，所以ArrayList 是支持快速访问、复制、序列化的。
 * 总结:
 * ArrayList 底层基于数组实现容量大小动态可变。 扩容机制为首先扩容为原始容量的 1.5 倍。
 * 如果1.5倍太小的话，则将我们所需的容量大小赋值给 newCapacity，
 * 如果1.5倍太大或者我们需要的容量太大，那就直接拿 newCapacity = (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE 来扩容。
 * 扩容之后是通过数组的拷贝来确保元素的准确性的，所以尽可能减少扩容操作。
 * ArrayList 的最大存储能力：Integer.MAX_VALUE。 size 为集合中存储的元素的个数。elementData.length 为数组长度，表示最多可以存储多少个元素。
 * 如果需要边遍历边 remove ，必须使用 iterator。且 remove 之前必须先 next，next 之后只能用一次 remove。
 * @Author agan
 * @Date 2021/3/25 21:12
 **/

public class ArrayListDemo {
    private static List<String> list = new ArrayList<>(10);


//    ArrayList 底层是基于 数组!!! 来实现容量大小动态变化的
//    size 是指 elementData 中实际有多少个元素，而 elementData.length 为集合容量，表示最多可以容纳多少个元素。

    private int size;  // 实际元素个数
    transient Object[] elementData;
//      默认初始化容量为10
    private static final int DEFAULT_CAPACITY = 10;
//    变量是定义在 AbstractList 中的。记录对 List 操作的次数。主要使用是在 Iterator，是防止在迭代的过程中集合被修改
    protected transient int modCount = 0;

//    第一次添加元素时知道该 elementData 从空的构造函数还是有参构造函数被初始化的。以便确认如何扩容。
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    //无参构造函数只是初始化了一个数组,在第一次添加时才将数组大小扩容10,
    //有参构造函数当initialCapacity > 0初始化该大小数组,  =0 时只是给了一个空数组EMPTY_ELEMENTDATA
    //将Collection转化为数组并赋值给elementData，把elementData中元素的个数赋值给size。
    // 如果 size 不为零，则判断 elementData 的 class 类型是否为 Object[]，不是的话则做一次转换。
    // 如果 size 为零，则把 EMPTY_ELEMENTDATA 赋值给 elementData，相当于new ArrayList(0)。
    private static List<String> list1 = new ArrayList<>(list);



    //从源码可以看出,ArrayList扩容的时候,先判断当前数组是不是为null,把初始化的大小和默认大小取最大值为扩容量,用这个扩容量与现在数组大小比较,如果现在数组大小不够用就进行扩容
    //扩容会按照现在数组大小的1.5倍进行扩容,如果扩容后的数组大小还是比扩容量小,就把数组大小设置为扩容量, 扩容量最大为21亿,否则会OutOfMemoryError
    //最后扩容后会把原数组内容放入新的数组.
    //所以在使用ArrayList的时候最好初始化大小,否则他会不断的进行Arrays.copyOf  浪费资源
    //remove(index)会先判断index是否下标越界,然后将旧的数组剔除这个下标后放到新的数组中.remove(Object) 则是逐个比对找出该值的index再进行remove
//    iterator.remove() 即可。因为在该方法中增加了 expectedModCount = modCount 操作。但是这个 remove 方法也有弊端。
//    1、只能进行remove操作，add、clear 等 Itr 中没有。
//    2、调用 remove 之前必须先调用 next。因为 remove 开始就对 lastRet 做了校验。而 lastRet 初始化时为 -1。
//    3、next 之后只可以调用一次 remove。因为 remove 会将 lastRet 重新初始化为 -1




    public static void main(String[] args){
        final ArrayList<String> strings = Lists.newArrayList("1", "2", "3", "4");
        strings.remove(3);
        strings.stream().forEach(System.out::println);
        final Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            String s= iterator.next();
            if (s.equals(3)) {
                iterator.remove();
            }
        }

    }
}
