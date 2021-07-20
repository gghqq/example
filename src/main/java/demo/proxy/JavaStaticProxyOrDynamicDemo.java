package demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName JavaStaticProxyDemo
 * @Description 典型的静态代理模式. 隐藏了真正实现.优点,简单
 * 缺点: 实现了代理类的接口,接口增加方法,代理类也要增加方法.而且一般情况下一个代理对象只能有一种类型的委托对象，所以要代理多个类型的对象就需要实现多个代理对象。
 *
 * 静态代理实现简单,但是代码复杂,每次当代理对象接口新增方法,实现类和代理类都要新增该方法.
 * 动态代理,把接口的所有方法都集中起来处理.
 * 动态代理类只能代理接口，代理类都需要实现InvocationHandler类，实现invoke方法。
 * 该invoke方法就是调用被代理接口的所有方法时需要调用的，该invoke方法返回的值是被代理接口的一个实现类
 *
 * @Author agan
 * @Date 2021/3/30 22:02
 **/

public class JavaStaticProxyOrDynamicDemo {
    public static void main(String[] args) {
        Hello h = new HelloWorld();
//    JAVA静态代理 代理类对实现类做代理
        StaticProxy p = new StaticProxy(h);
        p.sout();
//JAVA动态代理
//        实现动态代理时，我们使用了Proxy.newProxyInstance这个方法
//        loader： 类加载器
//        interfaces：  代理类要实现的接口列表
//        h: 代理处理handler
        Hello hp = (Hello) Proxy.newProxyInstance(
                  h.getClass().getClassLoader() //类加载器
                , h.getClass().getInterfaces()  //代理类要实现的接口列表
                , new InterfaceInvocationHandler(h));
        hp.sout();
        hp.sout2();
    }
}



interface Hello {
    void sout();
    void sout2();
}
class HelloWorld implements Hello {

    @Override
    public void sout() {
        System.out.println("买火车票");
    }

    @Override
    public void sout2() {
        System.out.println("买飞机票");
    }
}
//JAVA静态代理
class StaticProxy implements Hello {
    Hello hello;

    public StaticProxy(Hello hello) {
        this.hello = hello;
    }

    @Override
//    静态代理代理时会发现,接口每增加一个方法,实现类和代理类都要增加.
    public void sout() {
        System.out.println("你好啊,这里是火车票代售点");
        hello.sout();
        System.out.println("好的,收您5块手续费");
    }

    @Override
    public void sout2() {
        System.out.println("你好啊,这里是飞机票代售点");
        hello.sout();
        System.out.println("好的,收您5块手续费");
    }
}

//JAVA动态代理
class InterfaceInvocationHandler implements InvocationHandler {
    private Object object;

    public InterfaceInvocationHandler(Object o) {
        this.object = o;
    }

    /**
     * @param proxy:         这是需要被代理的对象
     * @param method：这是对象的方法
     * @param args:这是方法的参数列表
     * @return
     * @throws Throwable
     */
    @Override
//    不用重写接口的所有方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我代理了你的所有方法,我现在是总和代售点");
        return method.invoke(this.object, args);
    }
}

