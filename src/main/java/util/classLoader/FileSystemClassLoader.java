package util.classLoader;

import java.io.*;
import java.lang.reflect.Method;

/**
 * @ClassName A
 * @Description:
 * 类 FileSystemClassLoader继承自类java.lang.ClassLoader。在java.lang.ClassLoader类的常用方法中，一般来说，自己开发的类加载器只需要覆写 findClass(String name)方法即可。
 * java.lang.ClassLoader类的方法loadClass()封装了前面提到的代理模式的实现。该方法会首先调用findLoadedClass()方法来检查该类是否已经被加载过；
 * 如果没有加载过的话，会调用父类加载器的loadClass()方法来尝试加载该类；如果父类加载器无法加载该类的话，就调用findClass()方法来查找该类。
 * 因此，为了保证类加载器都正确实现代理模式，在开发自己的类加载器时，最好不要覆写 loadClass()方法，而是覆写 findClass()方法。
 * 类 FileSystemClassLoader的 findClass()方法首先根据类的全名在硬盘上查找类的字节代码文件（.class 文件），然后读取该文件内容，最后通过defineClass()方法来把这些字节代码转换成 java.lang.Class类的实例。加载本地文件系统上的类，
 * @Author agan
 * @Date 2021/4/28 23:06
 **/
// 文件系统类加载器
public class FileSystemClassLoader extends ClassLoader {

    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    // 获取类的字节码
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);  // 获取类的字节数组
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        // 读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            // 读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        // 得到类文件的完全路径
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }


    public static void main(String[] args) {
        new FileSystemClassLoader().testClassIdentity();
    }

    public void testClassIdentity() {
        String classDataRootPath = "C:\\Users\\JackZhou\\Documents\\NetBeansProjects\\classloader\\build\\classes";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
        FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath);
        String className = "com.example.Sample";
        try {
            Class<?> class1 = fscl1.loadClass(className);  // 加载Sample类
            Object obj1 = class1.newInstance();  // 创建对象
            Class<?> class2 = fscl2.loadClass(className);
            Object obj2 = class2.newInstance();
            Method setSampleMethod = class1.getMethod("setSample", java.lang.Object.class);
            setSampleMethod.invoke(obj1, obj2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


