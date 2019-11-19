package leetCode.easy;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/11
 * @Time: 8:59
 * @Description:   in 123  output 321
 **/
public class ReverseInteger {

        public static int num=123;

        public static void main(String[] args) {
            int z =123;
            System.out.println("变量 = " + 123/10);
            System.out.println("变量 = " + ( 0L-2147483648L ));
        }


        public int reverse(int x) {

            long result = 0;
            while (x != 0){
                // 第一次取余 res= 0+3  第二次 res=  3*10 +  12%10
                result = result * 10 +   x % 10 ;
                //   int类型舍弃小数位 12
                x /= 10; ;

            }

            if(result < Integer.MIN_VALUE || result > Integer.MAX_VALUE){
                return 0;
            }
            return (int)result;
        }

    public int reverse1(int x) {

        Long result = x < 0L ? 0L-x:x;
        //小于true  大于 false
        boolean tag = x < 0;

        StringBuilder sb = new StringBuilder(result.toString());
        Long reverse = Long.valueOf(sb.reverse().toString());
        if (tag){
            //小于0反转
            reverse =0L- reverse;
        }

        if(reverse < Integer.MIN_VALUE || reverse > Integer.MAX_VALUE){
            return 0;
        }else{
            return Integer.valueOf(reverse.intValue()) ;
        }
    }

}