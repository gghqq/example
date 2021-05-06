package demo;

import java.util.Arrays;

/**
 * @ClassName String  https://www.cnblogs.com/xiaoxi/p/6036701.html
 * @Description
 * String 每次操作都会生成新的对象,
 * StringBuilder 和 StringBuffer 则不会,如果频繁更改字符串内容建议使用StringBuilder
 * 单线程环境下推荐使用 StringBuilder，多线程环境下推荐使用 StringBuffer.
 * 从源码可以看出StringBuffer是继承了StringBuilder并在修改方法上加了synchronized
 *
 * @Author agan
 * @Date 2021/3/24 21:19
 **/

public class StringDemo {
    public static void main(String[] args){
        String s = "String,每次操作/都会生成:新的对象";
        System.out.println(s.hashCode());
        s = s + "1";
        System.out.println(s.hashCode());
        StringBuilder sb = new StringBuilder("StringBuilder不会生成新的对象    ");
        System.out.println(sb.hashCode());
        sb.append(1);
        System.out.println(sb.hashCode());
        StringBuffer sbf = new StringBuffer("StringBuffer不会生成新的对象");
        System.out.println(sbf.hashCode());
        sbf.append(1);
        System.out.println(sbf.hashCode());
        //split();根据字符串分割,里面可以是正则表达式,并行流不会按顺序输出
        Arrays.stream(s.split("[ .?,，/、:；]+")).parallel().forEach(System.out::println);
        //indexOf返回指定字符的索引,若没有则返回-1
        System.out.println(s.indexOf(0));
        System.out.println(s.indexOf(','));
        //charAt返回指定索引的字符,若
        System.out.println(s.charAt(6));
        //replace 替换
        System.out.println(s.replace("String","Stringggg"));
        //消除空格
        System.out.println(s.trim());
        //getBytes返回byte类型数字
        byte[] bytes = s.getBytes();
        //length()；返回字符串长度。
        System.out.println(s.length());
        //toLowerCase();将字符串转换为小写字母。
        //•toUpperCase();将字符串转换为大写字母。
        System.out.println(s.toLowerCase());
        System.out.println(s.toUpperCase());
        //substring 字符串截取
        System.out.println(s.substring(10,20));


    }
}
