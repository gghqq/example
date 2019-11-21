package concurrency.chapter11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/21
 * @Time: 11:08
 * @Description:
 **/
public class BooleanLock implements Lock {

    //The initValue is true indicated the lock have be get.
    //The initValue is false indicated the lock is free (other thread can get this.)
    private boolean initValue;

    private Collection<Thread> blockerThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue) {
            blockerThreadCollection.add(Thread.currentThread());
            this.wait();
        }

        blockerThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    // 拿锁 多少秒
    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0){
            lock();
        }
        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
        //如果锁被占用
        while (initValue) {
            if (hasRemaining <= 0)
                throw  new TimeOutException("Time out");
            blockerThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining =  endTime - System.currentTimeMillis() ;
        }
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread() == currentThread){
        this.initValue = false;
        Optional.of(Thread.currentThread().getName() + " release the lock  monitor ").ifPresent(System.out::println);
        this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(blockerThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockerThreadCollection.size();
    }
}