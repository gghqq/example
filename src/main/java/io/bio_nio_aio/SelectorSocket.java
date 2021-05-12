package io.bio_nio_aio;

import cn.hutool.socket.aio.AcceptHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName SelectorSocket
 * @Description Nio多路复用
 * @Author agan
 * @Date 2021/5/9 23:27
 **/

public class SelectorSocket {
    private static  ServerSocketChannel socketChannel = null;
    private static Selector selector = null;   //多路复用器.  linux内核下可以是poll,select,epoll   nginx可以是event{}
    static int port = 9090;


    public static void main(String[] args) throws IOException {
        start();
    }


    public static void init() throws IOException {
        socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);  //设置为非阻塞
        socketChannel.bind(new InetSocketAddress(port)); //绑定端口 bind

//        如果是在epoll模型下 epoll_create 内核里开辟了空间,  poll,select在java中开辟空间准备存储文件描述符,
        selector = Selector.open();
//        将channel注册到selector. 如果是在epoll多路复用器下,epoll_ctrl(fd3,ADD), poll,select在jvm中开辟数组fd4放进去
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public static void start() throws IOException {
        init();
        System.out.println("此时服务器启动成功`````````````````````");
        while (true) {
            Set<SelectionKey> keySet = selector.keys();
            System.out.println("keysize:" + keySet.size());
            //模拟selector在内核中循环,获取连接状态的key,或者发送状态的key,
            // select,poll当中是内核select(fd4),poll(fd4),
//            epoll中是epoll_wait()
            while (selector.select(500) > 0) {
                Set<SelectionKey> keySet1 = selector.selectedKeys(); //返回有状态的fd集合
                Iterator<SelectionKey> iterator = keySet1.iterator();
                while (iterator.hasNext()) { //逐个遍历每个有状态的IO
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {   //建立连接的IO
                        acceptHandler(next);
                    }
                    if (next.isReadable()) { //发送数据IO
                        readHandler(next);
                    }
                }
            }
        }
    }


    public static void acceptHandler(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel accept = ssc.accept(); //建立连接
        accept.configureBlocking(false); //设置为非阻塞
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        accept.register(selector,SelectionKey.OP_CONNECT, byteBuffer);
        System.out.println("-----------------------------------");
        System.out.println("新客户端: "+accept.getLocalAddress());
        System.out.println("-----------------------------------");
    }

    public static void readHandler(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer =  ByteBuffer.allocate(1024);

        while (true){
            buffer.clear();
            int n = client.read(buffer);//读取数据
            if (n <= 0) {
                break;
            }
            buffer.flip();
        }

    }

}
