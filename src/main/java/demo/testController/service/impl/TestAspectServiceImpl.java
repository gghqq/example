package demo.testController.service.impl;

import demo.testController.service.TestAspectService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestAspectServiceImpl
 * @Description
 * @Author wpj
 * @Date 2021/9/1 10:22
 **/

@Service
public class TestAspectServiceImpl implements TestAspectService {
    @Override
    public String doSomeThing(String s) {
        System.out.println("执行业务逻辑: " + s);
        return "处理完业务逻辑,返回的结果";
    }
}
