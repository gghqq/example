package io.bio_nio_aio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName SocketDemo  https://juejin.cn/post/6844904196932632589
 * @Description   bio
 * BIO是Blocking IO的意思。在类似于网络中进行read, write, connect一类的系统调用时会被卡住
 * 这种Block是不会影响同时运行的其他程序（进程）的，因为现代操作系统都是多任务的，任务之间的切换是抢占式的。这里Block只是指Block当前的进程。
 * 网络服务为了同时响应多个并发的网络请求，必须实现为多线程的。每个线程处理一个网络请求。线程数随着并发连接数线性增长。
 * 线程越多，Context Switch就越多，而Context Switch是一个比较重的操作，会无谓浪费大量的CPU。
 * 每个线程会占用一定的内存作为线程的栈。比如有1000个线程同时运行，每个占用1MB内存，就占用了1个G的内存。
 *
 * 所以BIO的核心问题在于 每连接,每创建线程,并在等待消息发送的过程中让出所占用的cpu时间片,并在线程上下文切换的过程中造成大量的资源浪费
 *
 *
 *
 * @Author agan
 * @Date 2021/5/9 21:22
 **/

public class BIOSocketDemo {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket socket = new ServerSocket(port); //创建一个socket连接
            while (true) {
                Socket accept = socket.accept(); //获取连接
                System.out.println("有链接进入:" + accept.getInetAddress());
                new Thread(() -> {  //bio情况下每有一个连接进来会创建一个线程去处理
                    InputStream inputStream = null;
                    BufferedReader bis = null;
                    try {
                        inputStream = accept.getInputStream();
                        bis = new BufferedReader(new InputStreamReader(inputStream));
                        while (true){ //模拟BIO阻塞,等待客户端发送内容
                            System.out.println(bis.readLine());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            bis.close();
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
