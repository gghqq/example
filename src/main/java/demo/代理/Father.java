package demo.代理;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

/**
 * @ClassName Father
 * @Description
 * @Author wpj
 * @Date 2021/7/8 16:16
 **/

public abstract class Father {


    public abstract void fmethod(String str) throws Exception;


    protected void proFmethod(String str){

        try {
            System.out.println("对子类实现的方法进行代理");
            fmethod(str);
            System.out.println("after");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sout(String str) {
        System.out.println("父类执行方法,子类传参");
    }


}
