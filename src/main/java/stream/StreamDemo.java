package stream;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @ClassName Stream
 * @Description
 * @Author agan
 * @Date 2021/1/26 19:30
 **/

public class StreamDemo {
    public static void main(String[] args){
        List<User> lists = Lists.newArrayList(new User(6, "张三",Lists.newArrayList("戴尔","惠普"))
                ,new User(1, "张三",Lists.newArrayList("联想","小米")));

        List<User> lists1 = Lists.newArrayList(new User(2, "李四",Lists.newArrayList("小米","联想"))
                ,new User(3, "王五",Lists.newArrayList("华为","鸿基"))
                //这里加一个与lists重复的数据
                ,new User(1, "张三",Lists.newArrayList("联想","小米")));

        // 计算这个list中出现 "张三" id的值的和
        int sum = lists.stream().filter(u -> "张三".equals(u.getName())).mapToInt(u -> u.getId()).sum();
        System.out.println(sum);

        //list1 与 lists 交集
        System.out.println("交集");
        lists.parallelStream().filter(lists1::contains).forEach(System.out::println);
        System.out.println("flatMap");
        //将list.computers合并
        lists.parallelStream().flatMap(l -> l.getComputers().parallelStream()).forEach(System.out::println);


        String a = "Not much of a cheese shop really, is it?";
        Arrays.stream(a.split("[ .?,]+")).forEach(System.out::println);
    }

}
class User{

    public User(Integer id, String name,List<String> computers) {
        this.id = id;
        this.name = name;
        this.computers = computers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getComputers() {
        return computers;
    }

    public void setComputers(List<String> computers) {
        this.computers = computers;
    }

    Integer id;
    String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", computers=" + computers +
                '}';
    }

    List<String> computers;

}
