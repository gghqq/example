package 系统设计相关.springAop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @ClassName TestAop
 * @Description:
 *  定义切面
 *  如果一个 类被@Aspect 标注, 则这个类就不能是其他 aspect 的 **advised object** 了, 因为使用 @Aspect 后, 这个类就会被排除在 auto-proxying 机制之外.
 * @Author wpj
 * @Date 2021/9/1 10:19
 **/

@Component
@Aspect
public class TestAop {


    //定义切点
    //execution 执行任务时触发, *返回任意类型 demo.testController.service.impl.包路径  *任意类 *任意方法 (..)任意参数
    @Pointcut("execution (* demo.testController.service.impl.*.*(..))")
    public void todo() {
        System.out.println("todo~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }




    @Before("todo()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }

//    @After("todo()")
    public void afterAdvice() {
        System.out.println("afterAdvice...");
    }

    @Around("todo()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("before");
        try {

            //执行目标方法
            // proceedingJoinPoint.proceed();
            //用新的参数值执行目标方法
            proceedingJoinPoint.proceed(new Object[]{"这里修改为传2"});
            //返回通知
            System.out.println("目标方法返回结果后...");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("after");
    }



//    // 匹配指定包中的所有的方法
//    execution(* com.xys.service.*(..))
//    // 匹配当前包中的指定类的所有方法
//    execution(* UserService.*(..))
//    // 匹配指定包中的所有 public 方法
//    execution(public * com.xys.service.*(..))
//    // 匹配指定包中的所有 public 方法, 并且返回值是 int 类型的方法
//    execution(public int com.xys.service.*(..))
//    // 匹配指定包中的所有 public 方法, 并且第一个参数是 String, 返回值是 int 类型的方法
//    execution(public int com.xys.service.*(String name, ..))
//    // 匹配指定包中的所有的方法, 但不包括子包
//    within(com.xys.service.*)
//    // 匹配指定包中的所有的方法, 包括子包
//    within(com.xys.service..*)
//    // 匹配当前包中的指定类中的方法
//    within(UserService)
//    // 匹配一个接口的所有实现类中的实现的方法
//    within(UserDao+)
//    // 匹配以指定名字结尾的 Bean 中的所有方法
//    bean(*Service)
//    // 匹配以 Service 或 ServiceImpl 结尾的 bean
//    bean(*Service || *ServiceImpl)
//    // 匹配名字以 Service 结尾, 并且在包 com.xys.service 中的 bean
//    bean(*Service) && within(com.xys.service.*)
}
