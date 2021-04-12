package thread.util;

/**
 * @ClassName ThreadLocalUtil
 * @Description 采用内存共享传递变量，不改变原有方法的结构
 * @Author agan
 * @Date 2021/4/12 21:45
 **/

public class ThreadLocalUtil {

        private static final ThreadLocal<Object> local = new ThreadLocal<>();

        public static void setLocal(Object o){
            local.set(o);
        }

        public static Object getLocal(){
            return local.get();
        }

        public static void clear(){
            local.remove();
        }

        public static void main (String[] args){
            final Thread t1 = new Thread(() -> {
                ThreadLocalUtil.setLocal("我带了个t1线程私有变量过来");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ThreadLocalUtil.getLocal());
                ThreadLocalUtil.clear();
            });
            final Thread t2 = new Thread(() -> {
                ThreadLocalUtil.setLocal("我带了个t2线程私有变量过来");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ThreadLocalUtil.getLocal());
                ThreadLocalUtil.clear();
            });
            t1.start();
            t2.start();
        }



}

