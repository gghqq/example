package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName AtomicIntegerDemo
 * @Description
 * @Author agan
 * @Date 2021/4/6 15:56
 **/

public class AtomicIntegerDemo {
    private static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args){
        System.out.println(ai.incrementAndGet());
    }
}
