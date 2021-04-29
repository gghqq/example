package demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Mixin;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName CglibProxy
 * @Description Cglib可以实现对类的直接代理
 * java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
 * 而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
 * !!! 因为cglib是修改字节码文件生成子类, 所以final修饰的类不可以被cglib代理.
 * @Author agan
 * @Date 2021/3/30 22:32
 **/

public class CglibProxy {

    static class CglibDemo{
        public void sout() {
            System.out.println("");
        }
        public void sout(int a) {
            System.out.println("这是int方法");
        }
        public void sout(String a) {
            System.out.println("这是String方法");
        }
    }
    public void getClassLoader(){
        System.out.println("通过文件加载到了该类");
    }

    static class MethodInterceptorImpl implements MethodInterceptor{

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("springAop");
            methodProxy.invokeSuper(o,objects);
            return null;
        }
    }



    public static void main(String[] args) throws InvocationTargetException {
//        指定代理的方法. CGLIB的反射,可以看到Cglib可以实现对类的直接代理
        CglibDemo cglibDemo = new CglibDemo();
        FastClass fastClass = FastClass.create(CglibDemo.class);
        FastMethod  fastMethod= fastClass.getMethod("sout",new Class[]{int.class});
        fastMethod.invoke(cglibDemo,new Object[]{20});

        fastMethod= fastClass.getMethod("sout",new Class[]{String.class});
        fastMethod.invoke(cglibDemo,new Object[]{"20"});

//        类的增强使用一个Enhancer对象来增强对象的能力，上面的代码在对象WeakClass的方法执行前会输出一行内容，这也算是对WeakClass类的一种增强吧。因为Cglib是通过继承类来实现的，
//        所以在intercept方法里面执行的是methodProxy.invokeSuper(o, objects)方法，从继承这个角度来说，final类型的方法是无法被增强的。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibDemo.class);
        enhancer.setCallback(new MethodInterceptorImpl());
        CglibDemo weakClass = (CglibDemo)enhancer.create();
        weakClass.sout("10");
//        Mixin。可以委托多个类给Cglib，然后在需要的时候获取想要的类就可以了，这更像是一个对象池，
//        Cglib的作用是正确判断申请的对象类型，然后从池子里面返回正确的类型
        Class[] classes = new Class[]{MethodA.class,MethodB.class};
        Object[] objects = new Object[]{new ImplA(),new ImplB()};
        Object o = Mixin.create(classes,objects);
        MethodA mA = (MethodA) o;
        MethodB mB = (MethodB) o;
        mA.sout();
        mB.soutB();

    }
}

interface MethodA{
    void sout();
}
interface MethodB{
    void soutB();
}
class ImplA implements MethodA{

    @Override
    public void sout() {
        System.out.println("实现A");
    }
}
class ImplB implements MethodB{

    @Override
    public void soutB() {
        System.out.println("实现B");
    }
}
