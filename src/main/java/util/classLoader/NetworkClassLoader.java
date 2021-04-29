package util.classLoader;

import util.classLoader.netWorkClassLoaderDemo.ICalculator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName B
 * @Description
 * 下面将通过一个网络类加载器来说明如何通过类加载器来实现组件的动态更新。
 * 即基本的场景是：Java 字节代码（.class）文件存放在服务器上，客户端通过网络的方式获取字节代码并执行。
 * 当有版本更新的时候，只需要替换掉服务器上保存的文件即可。通过类加载器可以比较简单的实现这种需求。
 * 类 NetworkClassLoader负责通过网络下载Java类字节代码并定义出Java类。
 * 在通过NetworkClassLoader加载了某个版本的类之后，一般有两种做法来使用它。第一种做法是使用Java反射API。另外一种做法是使用接口。
 * 需要注意的是，并不能直接在客户端代码中引用从服务器上下载的类，因为客户端代码的类加载器找不到这些类。使用Java反射API可以直接调用Java类的方法。
 * 而使用接口的做法则是把接口的类放在客户端中，从服务器上加载实现此接口的不同版本的类。
 * 在客户端通过相同的接口来使用这些实现类。我们使用接口的方式。
 * @Author agan
 * @Date 2021/4/28 23:13
 **/


public class NetworkClassLoader extends ClassLoader {

    private String rootUrl;

    public NetworkClassLoader(String rootUrl) {
        // 指定URL
        this.rootUrl = rootUrl;
    }

    // 获取类的字节码
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        // 从网络上读取的类的字节
        String path = classNameToPath(className);
        try {
            URL url = new URL(path);
            InputStream ins = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            // 读取类文件的字节
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        // 得到类文件的URL
        return rootUrl + "/"
                + className.replace('.', '/') + ".class";
    }


    public static void main(String[] args) {
        String url = "http://localhost:8080/ClassloaderTest/classes";
        NetworkClassLoader ncl = new NetworkClassLoader(url);
        String basicClassName = "util.classLoader.netWorkClassLoaderDemo.CalculatorBasic";
        String advancedClassName = "util.classLoader.netWorkClassLoaderDemo.CalculatorAdvanced";
        try {
            Class<?> clazz = ncl.loadClass(basicClassName);  // 加载一个版本的类
            ICalculator calculator = (ICalculator) clazz.newInstance();  // 创建对象
            System.out.println(calculator.getVersion());
            clazz = ncl.loadClass(advancedClassName);  // 加载另一个版本的类
            calculator = (ICalculator) clazz.newInstance();
            System.out.println(calculator.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}