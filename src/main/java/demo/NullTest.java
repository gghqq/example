package demo;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import elasticSearch.User;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName NullTest
 * @Description
 * @Author agan
 * @Date 2021/3/12 14:33
 **/

public class NullTest {
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
    }


}
