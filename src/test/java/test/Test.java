package test;

import java.math.BigDecimal;

/**
 * @ClassName Test
 * @Description
 * @Author wpj
 * @Date 2021/8/11 17:41
 **/

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        String s = new String("abc");

        String s1 = s.getClass().newInstance().replace("abc","abcd");

        s= s1;

        System.out.println(s);


        System.out.println(BigDecimal.TEN.compareTo(BigDecimal.ONE) == 1);

    }
}
