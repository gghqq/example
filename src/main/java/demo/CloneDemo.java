package demo;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

/**
 * @ClassName CloneDemo https://www.jianshu.com/p/94dbef2de298
 * @Description: 深拷贝浅拷贝
 * 浅拷贝实现拷贝的类需要实现Cloneable,并重写clone()方法.
 *
 * 即默认拷贝构造函数只是对对象进行浅拷贝复制(逐个成员依次拷贝)，即只复制对象引用而不复制资源。
 * 基础数据类型的修改互不影响.引用类型修改后会产生影响
 * 深拷贝的特点
 * (1) 对于基本数据类型的成员对象，基础类型的拷贝，其中一个对象修改该值，不会影响另外一个（和浅拷贝一样）。
 * (2) 对于引用类型，比如数组或者类对象，深拷贝会新建一个对象空间，然后拷贝里面的内容，
 * 所以它们指向了不同的内存空间。改变其中一个，不会对另外一个也产生影响。
 * (3) 对于有多层对象的，每个对象都需要实现 Cloneable 并重写 clone() 方法，进而实现了对象的串行层层拷贝。
 * (4) 深拷贝相比于浅拷贝速度较慢并且花销较大。
 *
 *  序列化拷贝:
 *  序列化拷贝:对象图写入到一个持久化存储文件中并且当需要的时候把它读取回来, 这意味着当你需要把它读取回来时你需要整个对象图的一个拷贝
 * 确保对象图中所有类都是可序列化的 无法序列化transient变量, 使用这种方法将无法拷贝transient变量
 *  性能问题。创建一个socket, 序列化一个对象, 通过socket传输它, 然后反序列化它，这个过程与调用已有对象的方法相比是很慢的。
 *  如果性能对你的代码来说是至关重要的，建议不要使用这种方式。它比通过实现Clonable接口这种方式来进行深拷贝几乎多花100倍的时间
 *
 *  new 和 clone的区别
 *  new 操作符的本意是分配内存。程序执行到 new 操作符时，首先去看 new 操作符后面的类型，因为知道了类型，才能知道要分配多大的内存空间。
分配完内存之后，再调用构造函数，填充对象的各个域，这一步叫做对象的初始化，
构造方法返回后，一个对象创建完毕，可以把他的引用（地址）发布到外部，在外部就可以使用这个引用操纵这个对象。
 * clone 在第一步是和 new 相似的，都是分配内存，调用 clone 方法时，分配的内存和原对象（即调用 clone 方法的对象）相同，
然后再使用原对象中对应的各个域，填充新对象的域，填充完成之后，clone方法返回，一个新的相同的对象被创建，
同样可以把这个新对象的引用发布到外部。
 * @Author agan
 * @Date 2021/3/24 21:30
 **/

public class CloneDemo {
    public static void main(String[] args) throws IOException {
        User u1 = new User("这是u1",new B("B对象"));
        User u2 = (User) u1.clone();
        User u3 = u1;  //引用拷贝
        System.out.println("生成了新的对象 :" + u1 + "  !!!  " + u2 + " 引用拷贝: " + u3);
        u2.b.setSex("深拷贝更改引用对象的内容不会相互影响");
        System.out.println(u1.b.getSex());
        System.out.println(u2.b.getSex());
        ObjectOutputStream oos = null;
        ObjectInputStream ois=null;
        try{
            //序列化拷贝,实际就是拷贝流
            //创建输入流和输出流
            //创建可序列化对象
            Student s =new Student(new Subject("流拷贝"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(s);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            Student s2 = (Student) ois.readObject();
            System.out.println(s2.s.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            oos.close();
            ois.close();
        }


    }
}

class User implements Cloneable{
    String name;

    B b;

    /**
     *  重写clone()方法
     * @return
     */
    @Override
    public Object clone() {
        //深拷贝
        try {
            User u = (User) super.clone();
            u.b = (B) b.clone();
            // 直接调用父类的clone()方法
            return u;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public User(String name,B b) {
        this.name = name;
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

}

class  B  implements Cloneable {
    public B(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    String sex;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //浅拷贝只需实现super.clone()
        return super.clone();
    }
}

class Subject implements Serializable {
    String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
};

class Student implements Serializable{
    public Subject getS() {
        return s;
    }

    public void setS(Subject s) {
        this.s = s;
    }

    public Student(Subject s) {
        this.s = s;
    }

    Subject s;
};

