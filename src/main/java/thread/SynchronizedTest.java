package thread;

/**
 * @ClassName SynchronizedTest  https://zhuanlan.zhihu.com/p/89152074
 * @Description
 *  Synchronized 的作用主要有三个:
 *  1.确保线程互斥的访问同步代码
 *  2.保证共享变量的修改能够及时可见
 *  3.有效解决重排序问题
 *
 * Synchronized 总共有三种用法:
 *  1.修饰普通方法
 *  2.修饰静态方法  静态方法的同步本质上是对类的同步（静态方法本质上是属于类的方法，而不是对象上的方法）
 *  3.修饰代码块
 *
 *  反编译后从原理上来分析synchronize是如何实现代码同步的.
 *  每个对象有一个监视器锁（Monitor），当 Monitor 被占用时就会处于锁定状态。
 *  线程执行 Monitorenter 指令时尝试获取 Monitor 的所有权，过程如下：
 *  如果 Monitor 的进入数为 0，则该线程进入 Monitor，然后将进入数设置为 1，该线程即为 Monitor 的所有者。
 *  如果线程已经占有该 Monitor，只是重新进入，则进入 Monitor 的进入数加 1。
 *  如果其他线程已经占用了 Monitor，则该线程进入阻塞状态，直到 Monitor 的进入数为 0，再重新尝试获取 Monitor 的所有权。
 *  执行 Monitorexit 的线程必须是 Objectref 所对应的 Monitor 的所有者。
 *  指令执行时，Monitor 的进入数减 1，如果减 1 后进入数为 0，那线程退出 Monitor，不再是这个 Monitor 的所有者。
 *  其他被这个 Monitor 阻塞的线程可以尝试去获取这个 Monitor 的所有权。
 *  当synchronize修饰方法时, 其常量池多了ACC_SYNCHRONIZED标识符.JVM 就是根据该标示符来实现方法的同步的：
 *  当方法调用时，调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置。
 *  如果设置了，执行线程将先获取 Monitor，获取成功之后才能执行方法体，方法执行完后再释放 Monitor。
 *  在方法执行期间，其他任何线程都无法再获得同一个 Monitor 对象。
 *  其实本质上没有区别，只是方法的同步是一种隐式的方式来实现，无需通过字节码来完成。
 *
 *
 *
 * @Author agan
 * @Date 2021/3/31 21:41
 **/

public class SynchronizedTest {

//    没有同步方法,仅普通方法, 两个线程启动后同时执行method1和method2
//    public void method1(){
//        System.out.println("method1 start");
//        try {
//            System.out.println("method1 execute");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method1 end");
//    }
//    public void method2(){
//        System.out.println("method2 start");
//        try {
//            System.out.println("method2 execute");
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method2 end");
//    }
//

//    同步普通方法后,等待method1执行后,才执行method2
//    虽然 method1和method2是两个不同方法,但是这两个方法都进行了同步,并且是通过同一个对象去调用的,所以调用的时候要去竞争同一个对象上的锁,
//    也就是互斥才能获取到锁.所以是顺序执行
//    public synchronized void method1(){
//        System.out.println("method1 start");
//        try {
//            System.out.println("method1 execute");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method1 end");
//    }
//    public  void method2(){
//        System.out.println("method2 start");
//        try {
//            System.out.println("method2 execute");
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method2 end");
//    }

//    静态方法（类）同步  先执行method1,在执行method2
//    静态方法的同步本质上是对类的同步（静态方法本质上是属于类的方法，而不是对象上的方法）
//    虽然是两个不同的对象执行method1和method2,但是这两个对象属于共同的实例,由于method1和method2都属于实例方法,
//    public synchronized static void method1(){
//        System.out.println("method1 start");
//        try {
//            System.out.println("method1 execute");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method1 end");
//    }
//    public synchronized static void method2(){
//        System.out.println("method2 start");
//        try {
//            System.out.println("method2 execute");
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("method2 end");
//    }
//
//    代码块同步 synchronized(this)
//    虽然两个方法同时开始执行,但是在执行到锁定代码块的位置,method2等method1执行完才开始执行
//    对于代码块的同步，实质上需要获取 Synchronized 关键字后面括号中对象的 Monitor。
//    由于这段代码中括号的内容都是 This，而 Method1 和 Method2 又是通过同一的对象去调用的，所以进入同步块之前需要去竞争同一个对象上的锁，因此只能顺序执行同步块。
    public void method1(){
        System.out.println("method1 start");
        try {
            synchronized (this){
                System.out.println("method1 execute");
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method1 end");
    }
    public  void method2(){
        System.out.println("method2 start");
        try {
            synchronized (this){
                System.out.println("method2 execute");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method2 end");
    }


    public static void main(String[] args){
        SynchronizedTest s = new SynchronizedTest();
        new Thread(() -> s.method1()).start();
        new Thread(() -> s.method2()).start();
//        虽然有两个对象,但是对静态方法的同步本质上是对类的同步,而不是类的对象的同步
//        SynchronizedTest s1 = new SynchronizedTest();
//        new Thread(()->s.method1()).start();
//        new Thread(()->s1.method2()).start();


    }
}
