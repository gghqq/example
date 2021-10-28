package demo.proxy;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName A
 * @Description
 * @Author wpj
 * @Date 2021/10/24 15:56
 **/

@Data
public class A extends Base {
    String sex;

    public static void main(String[] args) {
        A a = new A();
        a.sex = "sex";
        Base base = (Base)a;
        base.name="aa";
        if (base instanceof A) {
            System.out.println(((A)base).getSex());
        }

        System.out.println(StringUtils.substringAfter("select * from a where 1=1", "from"));
        System.out.println(StringUtils.substringAfterLast("select * from a where 1=1", "from"));
        System.out.println(StringUtils.substringAfterLast("select * from a where 1=1", "from"));
    }
}
