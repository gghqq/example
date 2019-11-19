package leetCode.easy;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/12
 * @Time: 15:19
 * @Description:   in 121    ouput true     in 123  ouput  321 != 123  false
 * **/
public class IsPalindrome {

    public boolean isPalindrome(int x) {
        //先去除小于0 和 不为0且末位不是0的
        if (x < 0  || (x != 0 && x % 10 ==0)){
            return false;
        }else if (x == 0){
            return true;
        }

        ReverseInteger reverseInteger = new ReverseInteger();
        return    x==reverseInteger.reverse(x);
    }



    public boolean IsPalindrome1(int x) {
        // Special cases:
        // As discussed above, when x < 0, x is not a palindrome.
        // Also if the last digit of the number is 0, in order to be a palindrome,
        // the first digit of the number also needs to be 0.
        // Only 0 satisfy this property.
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        //当位数为奇数时,通过此方法可以去掉忽略中间值
        //  1 > 12 false
        while(x > revertedNumber) {
            //  revertedNumber = 0 + 1 = 1    10 + 2
            //  x = 12   1
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        //1 != 12 false
        return x == revertedNumber || x == revertedNumber/10;
    }



    public boolean isPalindrome3(int x) {
        String str = String.valueOf(x);
        int start = 0;
        int end = str.length() - 1;
        while(start < end){
            //逐位前后比对
            if(str.charAt(start++) != str.charAt(end--)) {return false;}
        }
        return true;
    }

}