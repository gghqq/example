package demo;


import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @ClassName Stream
 * @Description
 * @Author agan
 * @Date 2021/1/26 19:30
 **/

public class StreamDemo {
    public static void main(String[] args){
        List<Uuser> lists = Lists.newArrayList(new Uuser(6, "张三",new BigDecimal(250),Lists.newArrayList("戴尔","惠普"))
                ,new Uuser(1, "张三",new BigDecimal(500),Lists.newArrayList("联想","小米")));

        List<Uuser> lists1 = Lists.newArrayList(
                new Uuser(2, "李四",new BigDecimal(100),Lists.newArrayList("小米","联想"))
                ,new Uuser(3, "王五",new BigDecimal(200),Lists.newArrayList("华为","鸿基"))
                ,new Uuser(3, "张三",new BigDecimal(400),Lists.newArrayList("华为","鸿基"))
                ,new Uuser(3, "张三",new BigDecimal(500),Lists.newArrayList("华为","鸿基"))
                //这里加一个与lists重复的数据
                ,new Uuser(1, "张三",new BigDecimal(300),Lists.newArrayList("联想","小米")));

        lists1.parallelStream().forEach(l->{
            StringBuilder s = new StringBuilder();
            l.setGroup(s.append(l.getId()).append(l.getName()).toString());
        });
        Map<String, List<Uuser>> collect = lists1.parallelStream().collect(Collectors.groupingBy(Uuser::getGroup));
        Iterator iterator = collect.entrySet().iterator();
        while (iterator.hasNext()) {
           Map.Entry next = (Map.Entry)iterator.next();
            List<Uuser>  value = (List<Uuser>) next.getValue();
            System.out.println(value.parallelStream().mapToDouble(value1-> value1.getShenjia().doubleValue()).sum());
        }
        lists.addAll(lists1);
        System.out.println(lists.size());
        lists.addAll(lists1);
        System.out.println(lists.size());

//
//        // 计算这个list中出现 "张三" id的值的和
//        int sum = lists.stream().filter(u -> "张三".equals(u.getName())).mapToInt(u -> u.getId()).sum();
//        System.out.println(sum);
//
//        //list1 与 lists 交集
//        System.out.println("交集");
//        lists.parallelStream().filter(lists1::contains).forEach(System.out::println);
//        System.out.println("flatMap");
//        //将list.computers合并
//        lists.parallelStream().flatMap(l -> l.getComputers().parallelStream()).forEach(System.out::println);
//
//
//        String a = "Not much of a cheese shop really, is it?";
//        Arrays.stream(a.split("[ .?,]+")).forEach(System.out::println);
    }

}

@Data
class Uuser{



    Integer id;
    String name;
    BigDecimal shenjia;
    String group;

    public Uuser(Integer id, String name, BigDecimal shenjia, List<String> computers) {
        this.id = id;
        this.name = name;
        this.shenjia = shenjia;
        this.computers = computers;
    }

    List<String> computers;

}
