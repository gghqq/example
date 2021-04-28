package JVM.classLoader;

import java.io.InputStream;

/**
 * @ClassName TestBean
 * @Description
 *
 * 1.由不同的类加载器加载的指定类还是相同的类型吗？
 * 不是相同类型,下面证明有系统类加载器和自定义类加载器.它们来自同一个Class文件，但依然是两个独立的类，因此做所属类型检查时返回false
 * 2.在代码中直接调用Class.forName(String name)方法，到底会触发那个类加载器进行类加载行为？
 * Class.forName(String name)默认会使用调用类的类加载器来进行类加载。
 * 3.在编写自定义类加载器时，如果没有设定父加载器，那么父加载器是谁？
 * 不指定父类加载器的情况下，默认采用系统类加载器。
 * 即使用户自定义类加载器不指定父类加载器，那么，同样可以加载如下三个地方的类：
 * <Java_Runtime_Home>/lib下的类；
 * <Java_Runtime_Home>/lib/ext下或者由系统变量java.ext.dir指定位置中的类；
 * 当前工程类路径下或者由系统变量java.class.path指定位置中的类。
 * 4.在编写自定义类加载器时，如果将父类加载器强制设置为null，那么会有什么影响？如果自定义的类加载器不能加载指定类，就肯定会加载失败吗？
 * JVM规范中规定如果用户自定义的类加载器将父类加载器强制设置为null，那么会自动将启动类加载器设置为当前用户自定义类加载器的父类加载器（这个问题前面已经分析过了）。
 * 同时，我们可以得出如下结论：即使用户自定义类加载器不指定父类加载器，那么，同样可以加载到<JAVA_HOME>/lib下的类，但此时就不能够加载<JAVA_HOME>/lib/ext目录下的类了。
 * 5.自定义类加载器时，一般有哪些注意点？
 * 1)、一般尽量不要覆写已有的loadClass(…)方法中的委派逻辑（Old Generation）
 * 2). 正确设置父类加载器
 * 3). 保证findClass(String name)方法的逻辑正确性   事先尽量准确理解待定义的类加载器要完成的加载任务，确保最大程度上能够获取到对应的字节码内容
 * 6.如何在运行时判断系统类加载器能加载哪些路径下的类？
 * 一是可以直接调用ClassLoader.getSystemClassLoader()或者其他方式获取到系统类加载器（系统类加载器和扩展类加载器本身都派生自URLClassLoader），
 * 调用URLClassLoader中的getURLs()方法可以获取到。二是可以直接通过获取系统属性java.class.path来查看当前类路径上的条目信息 ：System.getProperty(“java.class.path”)。
 * 7.如何在运行时判断标准扩展类加载器能加载哪些路径下的类？
 *  参考第二个main方法
 *
 * @Author agan
 * @Date 2021/4/28 22:17
 **/
    /* 开发自己的类加载器
    在前面介绍类加载器的代理委派模型的时候，提到过类加载器会首先代理给其它类加载器来尝试加载某个类，这就意味着真正完成类的加载工作的类加载器和启动这个加载过程的类加载器，有可能不是同一个。真正完成类的加载工作是通过调用defineClass来实现的；而启动类的加载过程是通过调用loadClass来实现的。前者称为一个类的定义加载器（defining loader），后者称为初始加载器（initiating loader）。在Java虚拟机判断两个类是否相同的时候，使用的是类的定义加载器。
    也就是说，哪个类加载器启动类的加载过程并不重要，重要的是最终定义这个类的加载器。两种类加载器的关联之处在于：一个类的定义加载器是它引用的其它类的初始加载器。如类 com.example.Outer引用了类 com.example.Inner，则由类 com.example.Outer的定义加载器负责启动类 com.example.Inner的加载过程。
    方法 loadClass()抛出的是 java.lang.ClassNotFoundException异常；方法 defineClass()抛出的是 java.lang.NoClassDefFoundError异常。
    类加载器在成功加载某个类之后，会把得到的 java.lang.Class类的实例缓存起来。下次再请求加载该类的时候，类加载器会直接使用缓存的类的实例，而不会尝试再次加载。也就是说，对于一个类加载器实例来说，相同全名的类只加载一次，即 loadClass方法不会被重复调用。
    在绝大多数情况下，系统默认提供的类加载器实现已经可以满足需求。但是在某些情况下，您还是需要为应用开发出自己的类加载器。比如您的应用通过网络来传输Java类的字节代码，为了保证安全性，这些字节代码经过了加密处理。这个时候您就需要自己的类加载器来从某个网络地址上读取加密后的字节代码，接着进行解密和验证，最后定义出要在Java虚拟机中运行的类来。下面将通过两个具体的实例来说明类加载器的开发。
     */
public class TestBean {

    public static void main(String[] args) throws Exception {
        // 一个简单的类加载器，逆向双亲委派机制
        // 可以加载与自己在同一路径下的Class文件
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name)
                    throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1)
                            + ".class";
                    System.out.println(filename);
                    InputStream is = getClass().getResourceAsStream(filename);
                    if (is == null) {
                        return super.loadClass(name);   // 递归调用父类加载器
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myClassLoader.loadClass("JVM.classLoader.TestBean")
                .newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof JVM.classLoader.TestBean);
    }



//    /**
//     * 如何在运行时判断标准扩展类加载器能加载哪些路径下的类？
//     */
//    public static void main(String[] args) {
//        try {
//            URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();
//            for (int i = 0; i < extURLs.length; i++) {
//                System.out.println(extURLs[i]);
//            }
//        } catch (Exception e) {
//            //…
//        }
//    }
}
