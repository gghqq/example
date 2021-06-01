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
    public static void main(String[] args){
//
//        User u1 = new User();
//        System.out.println(u1);
//        User u2 = new User();
//        System.out.println(u2);
//        User u3 = Optional.ofNullable(u1).orElse(u2);
//        System.out.println(u3);
//
//
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println(Optional.ofNullable("A").orElseGet(()->soutB()));

        String s = "";
        System.out.println(StringUtils.isEmpty(s));
        System.out.println(StringUtils.isBlank(s));
    }


    private static String soutB(){
        System.out.println("B");
        return "B";
    }

    private static class User{
    }
}
