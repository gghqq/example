package demo;

import java.util.Objects;

/**
 * @ClassName NullTest
 * @Description
 * @Author agan
 * @Date 2021/3/12 14:33
 **/

public class NullTest {
    public static void main(String[] args){
//        String[] a = {"1","2","3","4"};
//        System.out.println(a[3]);
//        假如此时我希望执行完A后执行B 才可以执行C
//        if(A = finish){
//            if(B = finish){
//               C todo
//            }
//        }优化后
//         if(A = finish && B==finish){
//             c todo
//         }
        int n = 0;
            do{
                System.out.println(n);
                n++;
            }while (n<10);


        System.out.println();
    }
}
