package demo;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName NullTest
 * @Description
 * @Author agan
 * @Date 2021/3/12 14:33
 **/

public class NullTest {
    public static void main(String[] args){
         ArrayList<Object> objects = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            System.out.println(objects.size());
        }else {
            System.out.println(11111);
        }
        int i = 0;
        System.out.println(++i);
        System.out.println(i);

    }
    private static BigDecimal subtract(BigDecimal amount, BigDecimal taxAmount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0 || taxAmount == null || taxAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return amount.subtract(taxAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }



}
