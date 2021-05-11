package io.bio_nio_aio;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @ClassName NIOSocketDemo
 * @Description:
 * NIO解决线程阻塞的问题, 通过轮询的方式去获取客户端连接与消息的发送
 * 若大量客户端连接并发送数据,通过轮询的方式则会对每个请求进行数据的处理.(而底层read是系统调用,需要频繁上下文切换)
 *  轮询时设置的休息时间不好把握,设置过短会造成系统资源浪费,过长则会导致客户端延迟过大
 * NIO仅仅是指IO API总是能立刻返回，不会被Blocking；
 * IO多路复用和NIO是要配合一起使用才有实际意义
 *  (倘若是多路复用器(select,poll)的情况下,通过一次系统调用,在内核里面循环文件描述符,然后通知服务器哪些连接可以读取数据)
 *  弊端:每次都要把所有的客户端连接的文件描述符发送给内核.   内核全量遍历
 *
 *
 * @Author agan
 * @Date 2021/5/9 22:11
 **/

public class NIOSocketDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> clients = Lists.newLinkedList();
        ServerSocketChannel ss = ServerSocketChannel.open();//java nio接口
        ss.bind(new InetSocketAddress(8080));
        ss.configureBlocking(false); //重点 设置noBlock;

        while (true){
            SocketChannel accept = ss.accept(); //设置非阻塞后一定会有返回值.
            if (accept == null) {
                Thread.sleep(1000);
                System.out.println("无客户端连接");
            }else{ //当有客户端连接
                accept.configureBlocking(false);//同样设置为非阻塞
                System.out.println(accept.socket().getInetAddress());
                clients.add(accept);
            }
//            *Nio模型下一个线程接受客户端连接并读写数据*****该线程负责的第二个事情************************
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);//可在堆内或者堆外
            for (SocketChannel client : clients) { //模拟nio下不停的去循环获取客户端数据
                 int read = client.read(byteBuffer); //设置为非阻塞后, 0:无数据, >0:有数据,-1:连接中断
                if ( read > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.limit()];
                    byteBuffer.get(bytes);
                    System.out.println(client.socket().getInetAddress()+" : " +new String(bytes));
                }
            }
        }
    }





}
