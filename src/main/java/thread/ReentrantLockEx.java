package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockEx
 * @Description  经典例子  爸爸妈妈去银行取钱
 * @Author agan
 * lock()  和 unlock() 成对出现，在 login(Thread currentUserThread) 登录方法中调用 lock() ,在 logout() 退出方法中调用了 unlock()
 * 也就是说 Lock 类的锁机制允许在不同的方法中加锁 和 解锁， 而 synchronized 关键字只能在同一个方法中加锁 和 解锁。
 *
 *
1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
3）lockInterruptibly()就稍微难理解一些。
先说说线程的打扰机制，每个线程都有一个 打扰 标志。这里分两种情况，
1. 线程在sleep或wait,join， 此时如果别的进程调用此进程的 interrupt（）方法，此线程会被唤醒并被要求处理InterruptedException；
(thread在做IO操作时也可能有类似行为，见java thread api)
2. 此线程在运行中， 则不会收到提醒。但是 此线程的 “打扰标志”会被设置， 可以通过isInterrupted()查看并 作出处理。
lockInterruptibly()和上面的第一种情况是一样的， 线程在请求lock并被阻塞时，如果被interrupt，则“此线程会被唤醒并被要求处理InterruptedException”。并且如果线程已经被interrupt，再使用lockInterruptibly的时候，此线程也会被要求处理interruptedException
 *
 * @Date 2021/4/9 22:40
 **/

public class ReentrantLockEx {
//     static NoBlockBank bank = new NoBlockBank();
//     static ReentrantlockBank bank = new ReentrantlockBank();
//     static Reentrantlock_tryLockBank bank = new Reentrantlock_tryLockBank();
//     static Reentrantlock_tryLockTimeBank bank = new Reentrantlock_tryLockTimeBank();
     static Reentrantlock_lockInterruptibly bank = new Reentrantlock_lockInterruptibly();
    public static void main(String[] args){
        Thread baba = new Thread(()-> {
            try {

                bank.getAccount(10000d);
//                bank.takeMoney(10000d);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被打断");
            }

        });
        Thread mama = new Thread(()-> {
            try {
                bank.getAccount(10000d);
//                bank.takeMoney(10000d);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被打断");
            }

        });
        baba.setName("baba");
        mama.setName("mama");
        baba.start();
        mama.start();
        mama.interrupt();
    }
}

//无锁的情况下在baba取走1W后,mama还能看到账户里面有1W,并取出
class NoBlockBank{
//    private static  double account = 10000d;
//    测试一下volatile
    private volatile static   double account = 10000d;

    public  void getAccount() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "查看到账户余额: " + account);
//        模拟查看动作
        Thread.sleep(2000);
    }

    public void takeMoney(double  m) throws InterruptedException {
        if (account < m ){
            System.out.println("账户余额不足");
        }
        account -= m;
        System.out.println(Thread.currentThread().getName() + "取走了: " + m +"  账户余额为:  " + account );
//        模拟取钱动作
        Thread.sleep(5000);
    }
}

//ReentrantLock可以在不同的方法里上锁解锁
class ReentrantlockBank{
    private static  double account = 10000d;
    private ReentrantLock  lock = new ReentrantLock();

    public  void getAccount() throws InterruptedException {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "查看到账户余额: " + account);
//        模拟查看动作
        Thread.sleep(2000);
    }

    public void takeMoney(double  m) throws InterruptedException {
        if (account < m ){
            System.out.println(Thread.currentThread().getName()+"来取款发现账户余额不足");
            return;
        }
        account -= m;
        System.out.println(Thread.currentThread().getName() + "取走了: " + m +"  账户余额为:  " + account );
//        模拟取钱动作
        Thread.sleep(5000);
        lock.unlock();
    }
}


//通过 tryLock() 判断是否可以获得锁, 能获得锁 返回 true 否则返回 false
class Reentrantlock_tryLockBank{
    private static  double account = 10000d;
    private ReentrantLock  lock = new ReentrantLock();

    public  void getAccount(double  m) throws InterruptedException {
        if (!lock.tryLock()){
            System.out.println( "有人正在操作账户,请耐心等待 " );
        }else{
            System.out.println(Thread.currentThread().getName() + "查看到账户余额: " + account);
//        模拟查看动作
            Thread.sleep(2000);
            takeMoney(m);
        }
    }

    public void takeMoney(double  m) throws InterruptedException {
        if (account < m ){
            System.out.println(Thread.currentThread().getName()+"来取款发现账户余额不足");
            return;
        }
        account -= m;
        System.out.println(Thread.currentThread().getName() + "取走了: " + m +"  账户余额为:  " + account );
//        模拟取钱动作
        Thread.sleep(5000);
        lock.unlock();
    }
}


//tryLockTime 等待多久后重新获取锁
class Reentrantlock_tryLockTimeBank{
    private static  double account = 10000d;
    private ReentrantLock  lock = new ReentrantLock();

    public  void getAccount(double  m) throws InterruptedException {
        if (!lock.tryLock(10, TimeUnit.SECONDS)){
            System.out.println(Thread.currentThread().getName()+"被提示有人正在操作账户,请耐心等待10秒" );
        }else{
            System.out.println(Thread.currentThread().getName() + "查看到账户余额: " + account);
//        模拟查看动作
            Thread.sleep(2000);
            takeMoney(m);
        }
    }

    public void takeMoney(double  m) throws InterruptedException {
        if (account < m ){
            System.out.println(Thread.currentThread().getName()+"来取款发现账户余额不足");
            return;
        }
        account -= m;
        System.out.println(Thread.currentThread().getName() + "取走了: " + m +"  账户余额为:  " + account );
//        模拟取钱动作
        Thread.sleep(5000);
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + "释放掉了锁");
    }
}

//要让 interrupt(); 中断起作用, 在登录获得锁的时候 要调用 lockInterruptibly() 允许中断等待的线程
class Reentrantlock_lockInterruptibly{
    private static  double account = 10000d;
    private ReentrantLock  lock = new ReentrantLock();

    public  void getAccount(double  m) throws InterruptedException {
        lock.lockInterruptibly();
        System.out.println(Thread.currentThread().getName() + "查看到账户余额: " + account);
//        模拟查看动作
        Thread.sleep(2000);
        takeMoney(m);
    }

    public void takeMoney(double  m) throws InterruptedException {
        if (account < m ){
            System.out.println(Thread.currentThread().getName()+"来取款发现账户余额不足,打断其他等待线程");

            return;
        }
        account -= m;
        System.out.println(Thread.currentThread().getName() + "取走了: " + m +"  账户余额为:  " + account );
//        模拟取钱动作
        Thread.sleep(5000);
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + "释放掉了锁");
    }
}