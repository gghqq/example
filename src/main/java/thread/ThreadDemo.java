package thread;

/**
 * @ClassName ThreadDemo
 * @Description
 *
 * 1）sleep()：线程睡眠一段时间，进入阻塞状态,其他线程(包括低优先级线程）可以执行；不释放锁
 * 2）yield()：线程睡眠一段时间，其他同优先级线程可以执行；不释放锁
 * 3）join()：等待另一个线程一段时间，然后执行本线程；会释放锁。 如果不加参数，等待线程执行完毕；
 * 线程执行sleep()方法后会转入阻塞状态，所以，执行sleep()方法的线程在指定的时间内肯定不会被执行，而yield()方法只是使当前线程重新回到可执行状态，
 * 所以执行yield()方法的线程有可能在进入到可执行状态后马上又被执行。
 * ---------------------------------------------------------------------------------------------------------------------------
 * 线程间通信：调用wait()，notify()和notifyAll()的任务在调用这些方法前必须拥有对象的锁。wait()，notify()及notifyAll()只能在synchronized语句中使用，
 * 注意，它们都是Object类的方法，而不是Thread类的方法。
 * 1）notify()：唤醒在等待该对象同步锁的线程
 * 2）notifyAll()：唤醒所有等待的线程
 * 3）wait()：本线程阻塞，释放锁，然后等待锁；
 *如果使用的是ReenTrantLock实现同步，使用ReenTrantLock.newCondition()获取一个Condition类对象，然后Condition的await()，signal()以及signalAll()分别对应上面的三个方法。
 *
 * * 死锁一般形式即线程1占用锁A，然后等待锁B；同时线程2占用锁B，等待锁A
 *
 *
 *
 * @Author agan
 * @Date 2021/4/9 15:05
 **/

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
//         Thread thread1 = new Thread(new Thread1());
//         Thread2 thread2 = new Thread2();
//         thread1.setName("thread1");
//         thread2.setName("thread2");
//         thread1.start();
//        /*查看线程是否存活*/
//        System.out.println(thread1.isAlive());
//        System.out.println(thread2.isAlive());
//        //主线程休息3秒
//        Thread.sleep(3000);
//        thread2.start();
//------------------------------------------------------------------------------------------
//         Thread3 thread3 = new Thread3();
//         Thread thread1 = new Thread(thread3);
//         Thread thread2 = new Thread(thread3);
//         thread1.setName("thread1");
//         thread2.setName("thread2");
//         thread1.start();
//         thread2.start();
//-------------------------------------------------------------------------------------------
//         Thread4 thread4 = new Thread4();
//         Thread thread1 = new Thread(thread4);
//         Thread thread2 = new Thread(thread4);
//         thread1.setName("thread1");
//         thread2.setName("thread2");
//         thread1.start();
//         thread2.start();
//
//--------------------------------------------------------------------------------------------
        Thread5 thread5 = new Thread5();
        Thread thread = new Thread(thread5);
        thread.setName("thread1");
        thread.start();
        synchronized (thread5.object){
            try {
                System.out.println("main获得锁之后");
//                main获得锁之后,thread开始执行,锁此时在main里面.无法获取造成死锁.
                thread.join();
                System.out.println("thread执行完之后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<100;i++){
                System.out.println("Test.main():"+i);
            }
        }


    }

}


/*实现接口方式创建线程*/
class Thread1 implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread1线程:  " + Thread.currentThread().getName());
    }
}

/*继承方式创建线程*/
class Thread2 extends Thread{
    public void run (){
        System.out.println("Thread2线程:  " + Thread.currentThread().getName());
    }
}

//线程的同步
class Thread3 implements  Runnable{
    private static int num = 20;
    private static Object object = new Object();
    public void run() {
        while(true){
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object) {
                if(num >0 ){
                    System.out.println("Thr3.run():"+Thread.currentThread().getName()+":"+num);
                    num--;
                }
            }
        }/*while结束*/
    }
}
class Thread4 implements Runnable{
    private static int num = 20;
    public void run() {
        while(true){
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prt();
        }/*while结束*/
    }
    public synchronized void prt(){
        if(num >0 ){
            System.out.println("Thr4.run():"+Thread.currentThread().getName()+":"+num);
            num--;
        }
    }
}


class Thread5 implements  Runnable{
    private static int num = 20;
    public static Object object = new Object();
    public void run() {
        while(true){
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1获得锁之前");
            synchronized (object){
                System.out.println("thread1获得锁之后");
                if(num >0 ){
                    System.out.println("Thr3.run():"+Thread.currentThread().getName()+":"+num);
                    num--;
                }else
                    break;
            }
        }/*while结束*/
    }
}
