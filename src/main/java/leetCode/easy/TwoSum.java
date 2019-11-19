package leetCode.easy;
import	java.util.HashMap;
import	java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/7
 * @Time: 19:16
 * @Description:   in {3,2,4},6    返回答案的下标output[1,2]
 **/
public class TwoSum {

    public static void main(String[] args) {

        int[] twoSum = twoSum1(new int[]{3,2,4},6);
        for (int i : twoSum) {
            System.out.println("变量 = " + i);
        }
    }


        //暴力破解
        public static int[] twoSum(int[] nums, int target) {
            //控制外层循环 循环数组次数
            for(int i = 0 ; i < nums.length ; i++ ){
                for(int z = i+1 ; z < nums.length ; z ++){
                    if(nums[z] + nums[i] == target){
                        return new int[]{i, z};
                    }
                }
            }
            return null;
        }


        public static int[] twoSum1(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<Integer,Integer> ();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i],i);  //下标为value
            }
            for (int i = 0; i < nums.length; i++) {
                int  an= target-nums[i];  //先求出对应的值
                //如果为不考虑下标为自己的情况会出现 6-3 =3 下标为自己的情况
                if (map.containsKey(an)  && map.get(an) != i ){
                    return  new int[]{map.get(an),i};
                }
            }
            return null;
        }


    public static int[] twoSum2(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<Integer,Integer> ();
        for (int i = 0; i < nums.length; i++) {
            int  an= target-nums[i];  //先求出对应的值
            //如果为不考虑下标为自己的情况会出现 6-3 =3 下标为自己的情况
            if (map.containsKey(an) ){
                return  new int[]{map.get(an),i};
            }
            //两者都走统一循环 可以合并一起  先判断 则不会出现下标为自己的情况
            map.put(nums[i],i);
        }
        return null;
    }
}