package 数据结构.queue.stack;

/**
 * @ClassName A
 * @Description
 * @Author agan
 * @Date 2021/3/18 14:34
 **/

import java.io.Serializable;

/**
 * Created by zejian on 2016/11/27.
 * Blog : http://blog.csdn.net/javazejian/article/details/53362993 [原文地址,请尊重原创]
 * 栈的链式实现
 */
public class LinkedStack<T> implements Stack<T> ,Serializable{

    private static final long serialVersionUID = 1911829302658328353L;

    private Node<T> top;

    private int size;

    public LinkedStack(){
        this.top=new Node<>();
    }

    public int size(){
        return size;
    }


    @Override
    public boolean isEmpty() {
        return top==null || top.data==null;
    }

    @Override
    public void push(T data) {
        if (data==null){
//            throw new StackException("data can\'t be null");
        }
        if(this.top==null){//调用pop()后top可能为null
            this.top=new Node<>(data);
        }else if(this.top.data==null){
            this.top.data=data;
        }else {
            Node<T> p=new Node<>(data,this.top);
            top=p;//更新栈顶
        }
        size++;
    }

    @Override
    public T peek()  {
        if(isEmpty()){
//            throw new EmptyStackException("Stack empty");
        }

        return top.data;
    }

    @Override
    public T pop() {
        if(isEmpty()){
//            throw new EmptyStackException("Stack empty");
        }

        T data=top.data;
        top=top.next;
        size--;
        return data;
    }


private static    class Node<T> {
        Node<T> next;
        T data;

        public Node(T data, Node<T> top) {
            this.data = data;
            this.next = top;
        }

        public Node(T data) {
            this.data = data;
        }

        public Node() {
        }
    }
    //测试
    public static void main(String[] args){
        LinkedStack<String> sl=new LinkedStack<>();
        sl.push("A");
        sl.push("B");
        sl.push("C");
        int length=sl.size();
        for (int i = 0; i < length; i++) {
            System.out.println("sl.pop->"+sl.pop());
        }
    }
}