package util.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UUIDUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static StringBuffer buf = new StringBuffer();

    private static Lock lock = new ReentrantLock();// 锁对象
    /**
     * 随机产生32位16进制字符串
     *
     */
    public static String getRandom32PK() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /**
     * 随机产生19长度位字符串，以时间开头
     *
     */
    public static String getRandomBeginTimePK() {
        lock.lock(); //加锁
        Date d = new Date();
        String timeStr = sdf.format(d);

        String random32 = getRandom32PK();
        Random random = new Random();
        buf.setLength(0);
        int n;
        for(int i = 0; i < 2; i++){
            n =  random.nextInt(random32.length()) -1;
            if(n < 0){
                n = 0;
            }
            buf.append(random32.substring(n, n+1));
        }

        lock.unlock();

        return timeStr + buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.getRandomBeginTimePK());
    }
}
