package demo;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @ClassName Stream
 * @Description
 * @Author agan
 * @Date 2021/1/26 19:30
 **/

public class StreamDemo {
    public static void main(String[] args) {
        List<Uuser> lists = Lists.newArrayList(new Uuser(6, "张三", new BigDecimal(250), Lists.newArrayList("戴尔", "惠普"))
                , new Uuser(1, "张三", new BigDecimal(500), Lists.newArrayList("联想", "小米")));
        List<Uuser> lists1 = Lists.newArrayList(
                new Uuser(2, "李四", new BigDecimal(100), Lists.newArrayList("小米", "联想"))
                , new Uuser(3, "王五", new BigDecimal(200), Lists.newArrayList("华为", "鸿基"))
                , new Uuser(3, "张三", new BigDecimal(400), Lists.newArrayList("华为", "鸿基"))
                , new Uuser(3, "张三", new BigDecimal(500), Lists.newArrayList("华为", "鸿基"))
                //这里加一个与lists重复的数据
                , new Uuser(1, "张三", new BigDecimal(300), Lists.newArrayList("联想", "小米")));
        System.out.println("根据id,姓名进行分组~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        lists1.parallelStream().forEach(l -> {
            StringBuilder s = new StringBuilder();
            l.setGroup(s.append(l.getId()).append(l.getName()).toString());
        });
        Map<String, List<Uuser>> collect = lists1.parallelStream().collect(Collectors.groupingBy(Uuser::getGroup));
        Iterator iterator = collect.entrySet().iterator();
        System.out.println("根据id,姓名进行分组结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("逐个输出key,和相同分组的身价和~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            System.out.println(next.getKey());
            List<Uuser> value = (List<Uuser>) next.getValue();
            System.out.println(value.parallelStream().mapToDouble(value1 -> value1.getShenjia().doubleValue()).sum());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        lists.addAll(lists1);
        System.out.println("合并后的大小:  "+ lists.size() +"~~~~~~~~~~~~~~~~~~~~");
        lists.addAll(lists1);
        System.out.println("二次合并后的大小:  "+ lists.size() +"~~~~~~~~~~~~~~~~~~~~");

        // 计算这个list中出现 "张三" id的值的和
        int sum = lists.stream().filter(u -> "张三".equals(u.getName())).mapToInt(u -> u.getId()).sum();
        System.out.println("合并后的张三Id的总和: " + sum + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //list1 与 lists 交集
        System.out.println("list,list1的交集~~~~~~~~~~~~~~~~~~~~~~~");
        lists.parallelStream().filter(lists1::contains).distinct().forEach(System.out::println);
        System.out.println("将lists 计算机类型合并后逐个输出~~~~~~~~~~~~~~~~~~~~~~~~~");
        //将list.computers合并
        lists.parallelStream().flatMap(l -> l.getComputers().parallelStream()).forEach(System.out::println);

        System.out.println("进行多符号匹配输出~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //这里是正则 可以匹配多种符号
        String a = "Not much of a cheese shop really, is it?";
        Arrays.stream(a.split("[ .?,]+")).forEach(System.out::println);
        System.out.println("多符号匹配结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        List<String> list = Lists.newArrayList("a","b","c","h");
        //Matching
         boolean isValid=list.stream().anyMatch(element->element.contains("h"));
        System.out.println("有任何一个匹配,anyMatch :" + isValid+"~~~~~~~~~~~~~~~~~~~");
        boolean isValidOne=list.stream().allMatch(element->element.contains("h"));
        System.out.println("全部都匹配,isValidOne :" + isValidOne+"~~~~~~~~~~~~~~~~~~~");
        boolean isValidTwo=list.stream().noneMatch(element->element.contains("h"));
        System.out.println("全都不匹配,isValidTwo :" + isValidTwo+"~~~~~~~~~~~~~~~~~~~");

        //Mapping map就是对Stream的值进行再加工,将加工过后的值作为新的Stream返回
         list.stream().map(element->convertElement(element)).forEach(System.out::println);
        //put只是简单的添加，当map中存在对应Key的时候，put会覆盖掉原本的value值。
        //而computeIfAbsent顾名思义，会检查map中是否存在Key值，如果存在会检查value值是否为空，如果为空就会将K值赋给value。
        Map<String,Integer> map = Maps.newHashMap();
        //源码  使用了Function
        Integer name = map.computeIfAbsent("name", s -> s.length());

//        Lists.transform 将一种List实体类 转换为另外一种List
        List<UuserDto> dictsDtoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(lists)) {
            //Function 没有指明参数和返回值的类型.
            // 如果需要传入特定的参数,可以使用IntFunction，LongFunction，DoubleFunction
            //如果需要返回特定的参数,可以使用ToIntFunction，ToLongFunction，ToDoubleFunction
            dictsDtoList = Lists.transform(lists, new Function<Uuser, UuserDto>() {
                @Override
                public UuserDto apply(Uuser dicts) {
                    UuserDto dto = new UuserDto();
                    BeanUtils.copyProperties(dicts, dto);
                    return dto;
                }
            });
        }
        //如果需要接受两个参数,一个返回值,可以使用BiFunction
        // 如果需要传入特定的参数,可以使用IntBiFunction，LongBiFunction，DoubleBiFunction
        //如果需要返回特定的参数,可以使用ToIntBiFunction，ToLongBiFunction，ToDoubleBiFunction
        Map<String,Integer> map1 = Maps.newHashMap();
        map1.put("alice",100);
        map1.put("tom",200);
        map1.put("Jack",300);
        map1.replaceAll((xingming,value)-> name.equals("tom") ? value:500);
        //使用@FunctionalInterface 来定义一个 Functional Interface\


        Uuser uuser = new Uuser();
        setU(uuser);
        System.out.println(uuser.toString());
    }
    private static String convertElement(String element){
        return "element"+"abc";
    }
    private static void  setU(Uuser element){
       element.setId(13123);
    }
}

@Data
@NoArgsConstructor
class Uuser {


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

@Data
@NoArgsConstructor
class UuserDto {


    Integer id;
    String name;
    BigDecimal shenjia;
    String group;

    public UuserDto(Integer id, String name, BigDecimal shenjia, List<String> computers) {
        this.id = id;
        this.name = name;
        this.shenjia = shenjia;
        this.computers = computers;
    }

    List<String> computers;

}