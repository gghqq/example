package demo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName hashCode
 * @Description:
 * hashCode() 的作用是获取哈希码，也称为散列码；它实际上是返回一个int整数。
 * 这个哈希码的作用是确定该对象在哈希表中的索引位置。hashCode() 定义在JDK的Object.java中，
 * 这就意味着Java中的任何类都包含有hashCode()函数。
 * 散列表存储的是键值对(key-value)，它的特点是：能根据“键”快速的检索出对应的“值”。这其中就利用到了散列码！（可以快速找到所需要的对象）
 *
 * 1、hashCode的存在主要是用于查找的快捷性，如Hashtable，HashMap等，hashCode是用来在散列存储结构中确定对象的存储地址的；
 * 2、如果两个对象相同，就是适用于equals(java.lang.Object) 方法，那么这两个对象的hashCode一定要相同；
 * 3、如果对象的equals方法被重写，那么对象的hashCode也尽量重写，并且产生hashCode使用的对象，一定要和equals方法中使用的一致，否则就会违反上面提到的第2点；
 * 4、两个对象的hashCode相同，并不一定表示两个对象就相同，也就是不一定适用于equals(java.lang.Object) 方法
 ，只能够说明这两个对象在散列存储结构中，如Hashtable，他们“存放在同一个篮子里”。
 *
 *
 * @Author agan
 * @Date 2021/3/25 20:52
 **/

public class HashCode {
    public final static void main(String[] args) {
        HashTest a = new HashTest();
        HashTest b = new HashTest();
        a.setA(1);
        b.setA(1);
        Set<HashTest> set = new HashSet<HashTest>();
        set.add(a);
        set.add(b);
        //如果只重写hashCode方法,会发现两个对象hashcode一样,表明放在同一篮子里,但是实际为两个对象的引用.
        System.out.println(a.hashCode() == b.hashCode());
        //没有重写equal方法只会比较引用相不相同,这样HashSet就失去意义了.
        System.out.println(a.equals(b));
        //只重写hashCode方法, set里面会存在两个对象. 在重写equal方法后,set只存在一个对象
        System.out.println(set);
    }

}
class  HashTest{
    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        HashTest hashTest = (HashTest) o;
//        return a == hashTest.a;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}
