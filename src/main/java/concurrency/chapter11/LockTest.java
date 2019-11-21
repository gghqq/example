package concurrency.chapter11;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/21
 * @Time: 14:38
 * @Description:
 **/
public class LockTest {
    public static void main(String[] args) throws InterruptedException {

        final  BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1","T4","T3","T2").forEach(
                name -> new Thread( () -> {
                    try {
//                        booleanLock.lock();
                        booleanLock.lock(10L);
                        Optional.of(Thread.currentThread().getName() + " have a monitor ").ifPresent(System.out::println);
                        work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Lock.TimeOutException e) {
                        Optional.of(Thread.currentThread().getName() + " time out ").ifPresent(System.out::println);
                    } finally {
                        booleanLock.unlock();
                    }
                },name).start()
        );


        //这种情况是考虑到其他线程有可能释放这个锁
//        Thread.sleep(100);
//        booleanLock.unlock();

    }

    public static  void  work(){
        try {
            Optional.of(Thread.currentThread().getName() + " is working.... ")
                  .ifPresent(System.out::println);
            Thread.sleep(20_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}