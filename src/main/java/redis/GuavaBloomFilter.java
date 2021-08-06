package redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @ClassName GuavaBloomFilter
 * @Description
 * 布隆过滤器原理
 * 将多个hash函数的值,分别存放在一个 二进制数组上面 ,对应的位置分别为 1.
 * 例如 hash1(baidu) = 3 hash1(baidu) = 10
 * 则 0 0 1 0 0 0 0 0 0 1   这时再来 baidu 就可以通过两次hash 判断是否存在
 *
 * 使用guava实现bloom过滤
 *
 *
 * @Author wpj
 * @Date 2021/8/5 22:12
 **/

public class GuavaBloomFilter {
    public static void main(String[] args) {
        //初始化布隆过滤器：100000,误差率为1%
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),100000,0.01);
        bloomFilter.put("10086");

        System.out.println(bloomFilter.mightContain("123456"));
        System.out.println(bloomFilter.mightContain("10086"));
/////////////////////////使用Redisson实现布隆过滤   //////////////////////////////////////////

//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://192.168.14.104:6379");
//        config.useSingleServer().setPassword("123");
//        //构造Redisson
//        RedissonClient redisson = Redisson.create(config);
//
//        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");
//        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
//        bloomFilter.tryInit(100000000L,0.03);
//        //将号码10086插入到布隆过滤器中
//        bloomFilter.add("10086");
//
//        //判断下面号码是否在布隆过滤器中
//        System.out.println(bloomFilter.contains("123456"));//false
//        System.out.println(bloomFilter.contains("10086"));//true

    }



}
