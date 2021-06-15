package demo;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName NullTest
 * @Description
 * @Author agan
 * @Date 2021/3/12 14:33
 **/

public class NullTest {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }

       List<Integer> list1 = Collections.synchronizedList(new ArrayList<>(list.size()));
//       List<Integer> list1 =new Vector<>(list.size());

        System.out.println("添加完毕!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        list.parallelStream().forEach(l->{
                list1.add(l);

        });
        Thread.sleep(1000);
        System.out.println("数据大小:  " +list1.size());
        list1.parallelStream().forEach(System.out::print);
    }


    private static String soutB(){
        System.out.println("B");
        return "B";
    }

    private static class User{
    }
}
