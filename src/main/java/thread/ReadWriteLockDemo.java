package thread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReadWriteLockDemo
 * @Description
 * @Author agan
 * @Date 2021/4/19 17:36
 **/

public class ReadWriteLockDemo {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final TheData theData = new TheData();
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                theData.get();
            }).start();
        }
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                theData.set(new Random().nextInt(1000));
            }).start();
        }
    }

}

class TheData {
    private Integer data = 0;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void get() {
        rwLock.readLock().lock();
//        rwLock.readLock().tryLock();
        try {
            System.out.println("ready to read: " + Thread.currentThread().getName() +" time   " +  System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " now Data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock(); //使用try finally防止死锁
        }
    }

    public void set(Integer data) {
        rwLock.writeLock().lock();
//        rwLock.readLock().tryLock();
        try {
            System.out.println("write to read: " + Thread.currentThread().getName()+" time   " + System.currentTimeMillis());
            Thread.sleep(1000);
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "  now Data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
