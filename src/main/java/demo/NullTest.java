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
    }


    private static String soutB(){
        System.out.println("B");
        return "B";
    }

    private static class User{
    }
}
