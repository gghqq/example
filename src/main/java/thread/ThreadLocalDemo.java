package thread;

/**
 * @ClassName ThreadLocalDemo  https://zhuanlan.zhihu.com/p/112754571
 * @Description:  ThreadLocal是一个线程局部变量线程局部变量(ThreadLocal)其实的功用非常简单，就是为每一个使用该变量的线程都提供一个变量值的副本，
 * 是Java中一种较为特殊的线程绑定机制，是每一个线程都可以独立地改变自己的副本，而不会和其它线程的副本冲突。
 *
 * ThreadLocal: 内部的一系列操作是通过内部类ThreadLocalMap操作的. ThreadLocalMap内部有个entry内部类,这个类继承了ThreadLoacl的弱引用和值,
 *  ThreadLocalMap通过一个entry数组来存储, key是弱引用的threadlocal，所以会被回收，而entry是被new出来的是强引用，所以不会被回收，
 *  由于线程的生命周期很长，ThreadLocal被垃圾回收后，在ThreadLocalMap里对应的Entry的键值会变成null，但是后续在也没有操作set、get等方法了，
 *  应该在我们不使用的时候，主动调用remove方法进行清理。不然会有内存泄露的风险。
 *
 *  最好把threadlocal设置为static，一个ThreadLocal实例对应当前线程中的一个TSO实例。
 *  因此，如果把ThreadLocal声明为某个类的实例变量（而不是静态变量），那么每创建一个该类的实例就会导致一个新的TSO实例被创建。
 *  设想一下，一个线程内创建两个类实例，这个类中定义了一个非stattic的变量，并把它set到自己的map中，会发生什么，没错两个重复的object，因为两次的threadlocal不一样。
 *  这也是为什么要使用static来标识
 *
 * @Author agan
 * @Date 2021/4/8 15:06
 **/

public class ThreadLocalDemo {
    private static final   ThreadLocal th = new ThreadLocal();

    public static void get(){
        th.get();
    }

    public static void set(Object o){
        th.set(o);
    }

    public static void clear(){
        th.remove();
    }

    /**
     * threadlocalmap 是 ThreadLocal的一个静态内部类.他的默认大小是16,threadlocalmap实际存储的是一个Entry[],阈值为16*2/3,
     * resize()方法得知每次扩容2倍,通过for循环把旧entry赋给扩容后的数组
     *
     * 返回当前线程对象,获取当前线程的ThreadLocalMap.如果不为null就对map赋值,否则初始化ThreadLocalMap
     * @param value
     */
    /**
     * 在entry中包含着threadlocal的弱引用和对应的值，弱引用是比软引用要弱，不管内存充不充足，都会被GC，
     * 因此当仅仅只有ThreadLocalMap中的Entry的key指向ThreadLocal的时候，ThreadLocal会进行回收的。
     */
//    public void set(T value) {
//        Thread t = Thread.currentThread();
//        ThreadLocalMap map = getMap(t);
//        if (map != null)
//            map.set(this, value);
//        else
//            createMap(t, value);
//    }
//
//    ThreadLocalMap getMap(Thread t) {
//        return t.threadLocals;
//    }
//

//    ThreadLocal.ThreadLocalMap threadLocals = null;

//        Entry结构
//    static class Entry extends WeakReference<ThreadLocal<?>> {
//        Object value;
//
//        Entry(ThreadLocal<?> k, Object v) {
//            super(k);
//            value = v;
//        }
//    }


    /**
     * ThreadLocalMap的set方法,先取key的hash，即threadlocal的hash,
     * 这个过程会原子的将atmoicInteger增加一个步长，然后与数组长度求与，找到对应的下标位置，如果这个位置的元素不为空，且这个位置的元素就是我想放的元素，就直接返回，
     * 如果这个地方的虚引用被GC了，我就把这个地方覆盖了，如果都不满足就往下取数组，直到取到一个空的地方，再建立一个新的entry放到这个地方。最后判断一下是不是要扩容。
     *
     * 因为key是弱引用的threadlocal，所以会被回收，而entry是被new出来的是强引用，所以不会被回收，
     * 由于线程的生命周期很长，如果我们往ThreadLocal里面set了很大很大的Object对象，虽然set、get等等方法在特定的条件会调用进行额外的清理，
     * 但是ThreadLocal被垃圾回收后，在ThreadLocalMap里对应的Entry的键值会变成null，
     * 但是后续在也没有操作set、get等方法了，所以最佳实践，应该在我们不使用的时候，主动调用remove方法进行清理。不然会有内存泄露的风险。
     */
//    private void set(ThreadLocal<?> key, Object value) {
//
//        Entry[] tab = table;
//        int len = tab.length;
//        int i = key.threadLocalHashCode & (len-1);
//
//        for (Entry e = tab[i];
//             e != null;
//             e = tab[i = nextIndex(i, len)]) {
//            ThreadLocal<?> k = e.get();
//
//            if (k == key) {
//                e.value = value;
//                return;
//            }
//
//            if (k == null) {
//                replaceStaleEntry(key, value, i);
//                return;
//            }
//        }
//
//        tab[i] = new Entry(key, value);
//        int sz = ++size;
//        if (!cleanSomeSlots(i, sz) && sz >= threshold)
//            rehash();
//    }

//    private static AtomicInteger nextHashCode =
//            new AtomicInteger();
//    private final int threadLocalHashCode = nextHashCode();
//    private static final int HASH_INCREMENT = 0x61c88647;

//    private static int nextHashCode() {
//        return nextHashCode.getAndAdd(HASH_INCREMENT);
//    }


    /**
     *     步骤（4）调用了Entry的clear方法，实际调用的是父类WeakReference的clear方法，作用是去掉对ThreadLocal的弱引用。
     *     步骤（6）是去掉对value的引用，到这里当前线程里面的当前ThreadLocal对象的信息被清理完毕了。
     *     代码（7）从当前元素的下标开始看table数组里面的其他元素是否有key为null的，有则清理。循环退出的条件是遇到table里面有null的元素。
     *     所以这里知道null元素后面的Entry里面key 为null的元素不会被清理。

     *     而且最好把threadlocal设置为static：
     *     我们知道，一个ThreadLocal实例对应当前线程中的一个TSO实例。因此，如果把ThreadLocal声明为某个类的实例变量（而不是静态变量），那么每创建一个该类的实例就会导致一个新的TSO实例被创建。
     *     设想一下，一个线程内创建两个类实例，这个类中定义了一个非stattic的变量，并把它set到自己的map中，会发生什么，没错两个重复的object，因为两次的threadlocal不一样。
     *     这也是为什么要使用static来标识
     */
//    private void remove(ThreadLocal<?> key) {
//
//        //(1)计算当前ThreadLocal变量所在table数组位置，尝试使用快速定位方法
//        Entry[] tab = table;
//        int len = tab.length;
//        int i = key.threadLocalHashCode & (len-1);
//        //(2)这里使用循环是防止快速定位失效后，变量table数组
//        for (Entry e = tab[i];
//             e != null;
//             e = tab[i = nextIndex(i, len)]) {
//            //(3)找到
//            if (e.get() == key) {
//                //(4)找到则调用WeakReference的clear方法清除对ThreadLocal的弱引用
//                e.clear();
//                //(5)清理key为null的元素
//                expungeStaleEntry(i);
//                return;
//            }
//        }
//    }
//
//    private int expungeStaleEntry(int staleSlot) {
//        Entry[] tab = table;
//        int len = tab.length;
//
//        //（6）去掉去value的引用
//        tab[staleSlot].value = null;
//        tab[staleSlot] = null;
//        size--;
//
//        Entry e;
//        int i;
//        for (i = nextIndex(staleSlot, len);
//             (e = tab[i]) != null;
//             i = nextIndex(i, len)) {
//            ThreadLocal<?> k = e.get();
//
//            //(7)如果key为null,则去掉对value的引用。
//            if (k == null) {
//                e.value = null;
//                tab[i] = null;
//                size--;
//            } else {
//                int h = k.threadLocalHashCode & (len - 1);
//                if (h != i) {
//                    tab[i] = null;
//                    while (tab[h] != null)
//                        h = nextIndex(h, len);
//                    tab[h] = e;
//                }
//            }
//        }
//        return i;
//    }
}
