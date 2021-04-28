package JVM.classLoader;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @ClassName JVMClassLoader  https://blog.csdn.net/justloveyou_/article/details/72217806
 * @Description
 * @link:JVM.classLoader.JVMClassLoader 详细内容
 * @Author agan
 * @Date 2021/4/28 21:39
 **/

public class JVMClassLoader extends ClassLoader {
//
//    //加载指定名称（包括包名）的二进制类型，供用户调用的接口
//    public Class<?> loadClass(String name) throws ClassNotFoundException{ … }
//
//    //加载指定名称（包括包名）的二进制类型，同时指定是否解析（但是这里的resolve参数不一定真正能达到解析的效果），供继承用
//    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException{ … }
//
//    //findClass方法一般被loadClass方法调用去加载指定名称类，供继承用
//    protected Class<?> findClass(String name) throws ClassNotFoundException { … }
//
//    //定义类型，一般在findClass方法中读取到对应字节码后调用，final的，不能被继承
////这也从侧面说明：JVM已经实现了对应的具体功能，解析对应的字节码，产生对应的内部数据结构放置到方法区，所以无需覆写，直接调用就可以了）
//    protected final Class<?> defineClass(String name, byte[] b, int off, int len) throws ClassFormatError{ … }


    /**
     * 源码可以看出双亲委托的实现机制
     */
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        return loadClass(name, false);
//    }
//
//    protected synchronized Class<?> loadClass(String name, boolean resolve)
//            throws ClassNotFoundException {
//
//        // 首先判断该类型是否已经被加载
//        Class c = findLoadedClass(name);
//        if (c == null) {
//            //如果没有被加载，就委托给父类加载或者委派给启动类加载器加载
//            try {
//                if (parent != null) {
//                    //如果存在父类加载器，就委派给父类加载器加载
//                    c = parent.loadClass(name, false);
//                } else {    // 递归终止条件
//                    // 由于启动类加载器无法被Java程序直接引用，因此默认用 null 替代
//                    // parent == null就意味着由启动类加载器尝试加载该类，
//                    // 即通过调用 native方法 findBootstrapClass0(String name)加载
//                    c = findBootstrapClass0(name);
//                }
//            } catch (ClassNotFoundException e) {
//                // 如果父类加载器不能完成加载请求时，再调用自身的findClass方法进行类加载，若加载成功，findClass方法返回的是defineClass方法的返回值
//                // 注意，若自身也加载不了，会产生ClassNotFoundException异常并向上抛出
//                c = findClass(name);
//            }
//        }
//        if (resolve) {
//            resolveClass(c);
//        }
//        return c;
//    }



    public static void main(String[] args) throws IOException {
        System.out.println(ClassLoader.getSystemClassLoader()); //系统类加载器
        System.out.println(ClassLoader.getSystemClassLoader().getParent()); //扩展类加载器
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent()); //由于启动类加载器无法被引用,所以是null
        final JVMClassLoader jvmClassLoader = new JVMClassLoader();
        System.out.println(jvmClassLoader.getClass().getClassLoader());
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getPath());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getAuthority());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getContent());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getDefaultPort());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getFile());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getQuery());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getRef());
        System.out.println(jvmClassLoader.getClass().getClassLoader().getSystemResource("ClassLoaderTest.txt").getUserInfo());
//        运行时动态扩展java应用程序有两种
//        1、反射 (调用java.lang.Class.forName(…)加载类)
//        Class.forName(driver);
//        2、用户自定义类加载器
    }

}
