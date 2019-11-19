package leetCode.easy;
import	java.util.HashMap;
import	java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/13
 * @Time: 14:47
 * @Description:
 **/
public class RomanToInt {

    public static void main(String[] args) {
        System.out.println(romanToInt("XVIII"));
    }


    /**
     *
     *    5 50 500            1 10 100 1000
     *    V L D  不会连续出现  I X C M    不会出现连续3个以上
     *
     *    1 2   3    4  5   6  7   8
     *    I II III  IV  V  VI VII VIII
     *   9   10   11   12  13   14   15 16  17    18     19
     *   IX   X   XI  XII XIII XIV  XV XVI XVII  XVIII   XIX
     *    其实只有添加到map里的这些组合  其他的单独计算就可以了
     *
     * @param s
     * @return
     */
    public static int romanToInt(String s) {

        Map<String, Integer> map = new HashMap<String, Integer> ();
        map.put("I",1);
        map.put("IV",4);
        map.put("V",5);
        map.put("IX", 9);
        map.put("X",10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);

        int num = 0;
        for (int i = 0; i < s.length();  ){
            //假如长度为 4    3+1 =4   2+1 < 4  说明剩两个 否则剩余一个  (剩下两个的时候进来匹配)
            if ( i+1 < s.length() &&  map.containsKey(s.substring(i,i+2))   ){
                num+= map.get(s.substring(i,i+2));
                i+=2;
            }else {
                num+= map.get(s.substring(i,i+1));
                i++;
            }
        }
            if (num < 0 || num > 3999){
                return 0;
            }else {
                return num;
            }

    };


    /**
     *    比较相邻   大于就是相减  小于就是相加
     * @param s
     * @return
     */
    public static int romanToInt1(String s) {
       Map<Character, Integer> romanVals = new HashMap<Character, Integer>();
        romanVals.put('I', 1);
        romanVals.put('V', 5);
        romanVals.put('X', 10);
        romanVals.put('L', 50);
        romanVals.put('C', 100);
        romanVals.put('D', 500);
        romanVals.put('M', 1000);
        int lastVal = 0, total = 0;
        char[] charArr = s.toCharArray();
        for (int i=charArr.length-1; i>=0;i--) {
            //curVal = 第一位
            int curVal = romanVals.get(charArr[i]);

            if (curVal < lastVal) {
                total -= curVal;
            } else {
                //第一次相加
                total += curVal;
            }
            lastVal = curVal;
        }
        return total;
    }


    //这个思路和上面思路一致 累加相邻
    public int romanToInt3(String s) {
        int c = 0;            // count my current number

        for (int i = 0;i < s.length();i++) {
            char ch = s.charAt(i);
            char n = '0';   // n concerns about next character of the string and initialized to a random character
            if(i < s.length()-1) {
                n = s.charAt(i+1);  // n is initialized
            }
            if(ch == 'I') {
                if(n == 'V'){
                    c += 4;     // when IV it count as 4
                    i++;
                    continue;
                }
                if(n == 'X') {
                    c += 9;    // case of IX
                    i++;
                    continue;
                }
                c += 1;        //else case of I
                continue;
            }
            if(ch == 'X') {
                if(n == 'L'){
                    c += 40;            // case of XL
                    i++;
                    continue;
                }
                if(n == 'C') {
                    c += 90;               // case of XC
                    i++;
                    continue;
                }
                c += 10;                  // case of X
                continue;
            }
            if(ch == 'C') {
                if(n == 'D'){
                    c += 400;              // case of CD
                    i++;
                    continue;
                }
                if(n == 'M') {
                    c += 900;               // case of CM
                    i++;
                    continue;
                }
                c += 100;                // case of C
                continue;
            }
            if(ch == 'V') {
                c += 5;                   // case of V
                continue;
            }
            if(ch == 'L') {
                c += 50;                 // case of L
                continue;
            }
            if(ch == 'D') {
                c += 500;              // case of D
                continue;
            }
            if(ch == 'M') {
                c += 1000;            //case of M
                continue;
            }
        }
        return c;
    }
}