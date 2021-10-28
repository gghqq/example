package demo;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import elasticSearch.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName NullTest
 * @Description
 * @Author agan
 * @Date 2021/3/12 14:33
 **/

public class NullTest {

    private static final DecimalFormat percentFormat = new DecimalFormat();

    static {

        percentFormat.applyPattern("#0.00%");
    }
    public static void main(String[] args) throws InterruptedException {

        List<elasticSearch.User> list = Lists.newArrayList(
                new elasticSearch.User("11",123),new elasticSearch.User("11",123),
                new elasticSearch.User(null,123),new elasticSearch.User("",123)

        );
        List<User> collect = list.parallelStream().filter(l-> StringUtils.isNotEmpty(l.getName())).filter(l -> l.getName().equals("11")).collect(Collectors.toList());

//        System.out.println(JSON.toJSONString(list));

        Integer i = 30000;
        Integer z = 500000;
        System.out.println(i>z);

        List<String> objects = Lists.newArrayList();
        objects.add("123");
        objects.add("456");
        List<String> objects1 = Lists.newArrayList();
        objects.add("123");
        objects.add("456");
        System.out.println(CollectionUtils.isEmpty(objects));
        System.out.println(objects.size());
        System.out.println(objects.contains("123"));
        System.out.println(objects.contains("789"));
        System.out.println(objects.containsAll(objects1));
        System.out.println(DateUtil.thisYear()+"-11-01");
        System.out.println((DateUtil.thisYear()+1)+"-04-30");


        System.out.println(DateUtil.parse("2021-03-31","yyyy-MM-dd").toString());

        List a = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);

        a.forEach(System.out::println);



        JSONArray json = JSONArray.parseArray("[{\"kpiSettingName\":\"当期新风费资源\",\"kpiYearBudgetTotal\":\"14012907.19\",\"kpiYearExecuteTotal\":\"14560372.02\"},{\"kpiSettingName\":\"当期供暖费资源\",\"kpiYearBudgetTotal\":\"3714939.945\",\"kpiYearExecuteTotal\":\"3910463.1\"},{\"kpiSettingName\":\"当期物业费资源\",\"kpiYearBudgetTotal\":\"4967241.572\",\"kpiYearExecuteTotal\":\"5071259.89\"}]");
        System.out.println(percentFormat.format(3.15d));

        System.out.println(new BigDecimal(3.15).doubleValue());

        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(BigDecimal.ZERO.compareTo(BigDecimal.ZERO)==0);


        Map<String,Integer> map = Maps.newHashMap();
//        put只是简单的添加，当map中存在对应Key的时候，put会覆盖掉原本的value值。
//        而computeIfAbsent顾名思义，会检查map中是否存在Key值，如果存在会检查value值是否为空，如果为空就会将K值赋给value。
        Integer name = map.computeIfAbsent("name", s -> s.length());


        System.out.println("computeIfAbsent~~~~~~~ : " + name);

    }


}
