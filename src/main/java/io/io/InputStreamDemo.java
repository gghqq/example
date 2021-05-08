package io.io;


import java.io.*;

/**
 * @ClassName InputStreamDemo
 * @Description
 * @Author agan
 * @Date 2021/5/7 22:23
 **/

public class InputStreamDemo {
    private static FileInputStream fis = null;
    private static FileReader fr = null;
    private static FileOutputStream fos = null;
    private static BufferedInputStream bis = null;
    private static BufferedOutputStream bos = null;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        fwRead("D:\\work-space\\idea-work-space\\example\\src\\main\\java\\io\\IO流"); //字符流读取
//        fisRead("D:\\work-space\\idea-work-space\\example\\src\\main\\java\\io\\IO流",
//                "D:\\work-space\\idea-work-space\\example\\src\\main\\java\\io\\IO流_copy"); //字节流读取
//        bufferRead("D:\\work-space\\idea-work-space\\example\\src\\main\\java\\io\\IO流",
//                "D:\\work-space\\idea-work-space\\example\\src\\main\\java\\io\\IO流_copy"); //缓冲流读取
//        inputStreamReader();//转换流
//        objectStream(); //对象流的使用
    }

    //字符输入流
    public static void fwRead(String path) throws IOException {
        try {
            fr = new FileReader(path); //字符输入流
            char[] b = new char[1024]; //同样创建-个竹筒
            int read = 0;       //用来存储竹筒里面的内容
            while ((read = fr.read(b)) > 0) {  //存储竹筒盛的内容且>0
                System.out.println(new String(b, 0, read));
            }
        } catch (Exception exception) {

        } finally {
            fr.close();
        }
    }


    //字节输入流和字节输出流
    public static void fisRead(String readPath, String writePath) throws IOException {
        try {
            fis = new FileInputStream(readPath);  //创建字节输入流
            fos = new FileOutputStream(writePath);  //创建字节输出流

            byte[] bytes = new byte[1024]; //创建一个长度为1024的竹筒
            int hasRead = 0;  //用于保存的实际字节数
            while ((hasRead = fis.read(bytes)) > 0) { //模拟不停的用该竹筒从输入流中取数据
                System.out.println(new String(bytes, hasRead));  //取出竹筒中的水滴（字节），将字节数组转换成字符串进行输出
                fos.write(bytes, 0, hasRead); //每读取一次，即写入文件输入流，读了多少，就写多少。  字符输出流类似
            }
        } catch (IOException e) {

        } finally {
            fis.close();
            fos.close();
        }
    }


    //字节输入流和字节输出流 将缓冲流套在字符输入输出文件节点流上使用
    public static void bufferRead(String readPath, String writePath) throws IOException {
        try {
            fis = new FileInputStream(readPath);  //创建字节输入流
            fos = new FileOutputStream(writePath);  //创建字节输出流
            bis = new BufferedInputStream(fis); //套上节点流
            bos = new BufferedOutputStream(fos);

            byte[] bytes = new byte[1024]; //创建一个长度为1024的竹筒
            int hasRead = 0;  //用于保存的实际字节数
            while ((hasRead = bis.read(bytes)) > 0) { //模拟不停的用该竹筒从输入流中取数据
                System.out.println(new String(bytes, hasRead));  //从缓冲流中读取
                bos.write(bytes, 0, hasRead); //向缓冲流中写
            }
        } catch (IOException e) {

        } finally {
            bis.close();  //关闭流时需先关闭缓冲流,  缓冲流的关闭会执行flush方法
            bos.close();
            fis.close();  //  再关闭节点流
            fos.close();
        }
    }

    //转换流
    public static void inputStreamReader() throws IOException {
        // 将System.in对象转化为Reader对象
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            String t = null;
            while ((t = br.readLine()) != null) {
                if (t.equals("exit")) {
                    System.exit(1);
                }
                System.out.println(t);
            }
        } catch (Exception e) {

        } finally {
            br.close();
            isr.close();
        }
    }

    //对象流的使用
    public static void objectStream() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); //创建一个字节流
        ObjectOutputStream oos = new ObjectOutputStream(bos);   //将对象流绑定到字节流上

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois= new ObjectInputStream(bis);
        try {
            User u = new User("对象流",20);
            oos.writeObject(u); //写入对象流
            oos.flush();

            User o = (User) ois.readObject();
            System.out.println(o.name);
            System.out.println(o.a);
        }catch (Exception e){

        }finally {
            oos.close();
            ois.close();
            bos.close();
            bis.close();
        }
    }
}


class User implements Serializable{
    String name;
    transient  int a ;
    public User(String name, int a){
        this.name = name;
        this.a = a;
    }
}
