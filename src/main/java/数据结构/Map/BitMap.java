package 数据结构.Map;

import cn.hutool.bloomfilter.BitMapBloomFilter;

/**
 * @ClassName BitMap
 * @Description :
 * 首先要知道在java中,各种数据类型的大小是字节表示.最小的存储单位是byte  也就是8bit
 * 1byte(字节) = 8bit(比特位)    所以一个int 类型占用就是 4byte * 8bit = 32bit
 * 如果我们用bit的位置来表示数字 这样 1个int类型就可以表示32个数字
 * 在一些数据量比较大的场景中，做一些查重、排序，一般的方法难以实现。数据量过大，会占用较大的内存，
 * 常用的处理方式有两种：BitMap（位图法）和 布隆过滤   数据量比较大的场景中，做一些查重、排序，
 * @Author wpj
 * @Date 2021/7/18 22:30
 **/

public class BitMap {

    }
