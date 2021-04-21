package JVM内存结构;

/**
 * @ClassName stack  https://blog.csdn.net/rongtaoup/article/details/89142396
 * @Description Java 虚拟机栈为 JVM 执行 Java 方法服务，
 * 本地方法栈则为 JVM 使用到的 Native 方法服务。Native 方法是以本地语言实现的（比如 C 或 C++）。个人理解Native 方法是与操作系统直接交互的。
 * 什么是栈?
 * 栈的定义：限定仅在表头进行插入和删除操作的线性表。即压栈（入栈）和弹栈（出栈）都是对栈顶元素进行操作的。所以栈是后进先出的。
 * 栈是线程私有的，他的生命周期与线程相同。每个线程都会分配一个栈的空间，即每个线程拥有独立的栈空间。
 * 栈中存储的是什么?
 * 栈帧是栈的元素。每个方法在执行时都会创建一个栈帧。栈帧中存储了局部变量表、操作数栈、动态连接和方法出口等信息。每个方法从调用到运行结束的过程，就对应着一个栈帧在栈中压栈到出栈的过程。
 * 1.局部变量表
 * 1.1栈帧中，由一个局部变量表存储数据。局部变量表中存储了基本数据类型的局部变量（包括参数）、和对象的引用（String、数组、对象等），但是不存储对象的内容。
 * 局部变量表所需的内存空间在编译期间完成分配，在方法运行期间不会改变局部变量表的大小。
 * 1.2 局部变量的容量以变量槽（Variable Slot）为最小单位，每个变量槽最大存储32位的数据类型。对于64位的数据类型（long、double），JVM 会为其分配两个连续的变量槽来存储。以下简称 Slot 。
 * 1.3 JVM 通过索引定位的方式使用局部变量表，索引的范围从0开始至局部变量表中最大的 Slot 数量。普通方法与 static 方法在第 0 个槽位的存储有所不同。非 static 方法的第 0 个槽位存储方法所属对象实例的引用。
 * 1.4 局部变量表中的 Slot 是可以复用的。方法中定义的局部变量，其作用域不一定会覆盖整个方法。当方法运行时，如果已经超出了某个变量的作用域，即变量失效了，那这个变量对应的 Slot 就可以交给其他变量使用，也就是所谓的 Slot 复用
 * 2.操作数栈
 * 2.1 操作数栈是一个后进先出栈。操作数栈的元素可以是任意的Java数据类型。方法刚开始执行时，操作数栈是空的，在方法执行过程中，通过字节码指令对操作数栈进行压栈和出栈的操作。
 * 通常进行算数运算的时候是通过操作数栈来进行的，又或者是在调用其他方法的时候通过操作数栈进行参数传递。操作数栈可以理解为栈帧中用于计算的临时数据存储区。
 *
 * 栈中可能出现哪些异常？
 * StackOverflowError：栈溢出错误 栈内空间不足
 * OutOfMemoryError：内存不足   栈进行动态扩展时如果无法申请到足够内存，会抛出 OutOfMemoryError 异常。
 *
 * 如何设置栈参数？
 * 使用 -Xss 设置栈大小，通常几百K就够用了。由于栈是线程私有的，线程数越多，占用栈空间越大。
 *
 *
 *
 *
 * @Author agan
 * @Date 2021/4/21 16:32
 **/

public class stackDemo {

    /*
      栈帧中局部变量表存储基础数据类型（boolean、byte、char、short、int、float、long、double）和对象的引用(String,Object,数组)
      局部变量表的最小单位是变量槽（Variable Slot）,每个变量槽最大存储32位(4字节)的数据类型.  long 和 double是9字节.所以占用2变量槽
     */
    public static int runState(int i, long l, double d, Object o, byte b) {
        return 0;
    }

    /*
    通方法与 static 方法在第 0 个槽位的存储有所不同。非 static 方法的第 0 个槽位存储方法所属对象实例的引用。
     */
    public int runReference(char c, short s, boolean b) {
        return 0;
    }

    /*
    变量槽复用
    Slot 复用虽然节省了栈帧空间，但是会伴随一些额外的副作用。比如，Slot 的复用会直接影响到系统的垃圾收集行为。
     */
    public void test(boolean flag) {
        if (flag) {
            //{}为a的作用域
            int a = 66;
        }
        //已超出a的作用域,此时a所占用的变量槽slot会交给b使用,这就是 Slot 复用。
        int b = 55;
    }


    public static void main(String[] args) {
        byte[] placeholder = new byte[64 * 1024 * 1024]; //给个64M空间,  设置vm -options -verbose:gc
        System.gc();//当执行 System.gc() 方法时，变量 placeholder 还在作用域范围之内，虚拟机是不会回收的，它还是“有效”的。

        /*
        2. 限定了 placeholder 的作用域，但之后并没有任何对局部变量表的读写操作，placeholder 变量在局部变量表中占用的Slot没有被其它变量所复用，所以作为 GC Roots 一部分的局部变量表仍然保持着对它的关联。所以 placeholder 变量没有被回收。
         */
        {
            byte[] placeholder1 = new byte[64 * 1024 * 1024];
        }
        int a = 0;//第三次修改 a复用placeholder1 占用的 Slot,局部变量表中的 Slot 已经没有 placeholder1 的引用了，虚拟机就回收了placeholder1 占用的 64M 内存空间。
        System.gc();
    }

    /*
       反编译后查看  @picture/
       add 方法刚开始执行时，操作数栈是空的。当执行iload_0时，从局部变量表中获取变量 a的值压入操作栈。然后执行 iload_1，从局部变量表中获取变量b的值压入操作栈。
      接着执行iadd，弹出两个变量（a和b出操作数栈）进行求和，然后将结果c压入操作数栈。然后执行istore_2，弹出结果（出栈）。把值放入局部变量表
     */
    public static int add(int a, int b){
        int c = a + b;
        return c;
    }

}
