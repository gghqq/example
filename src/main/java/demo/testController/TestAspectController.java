package demo.testController;

import demo.testController.service.TestAspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestAspectController
 * @Description
 * @Author wpj
 * @Date 2021/8/31 20:49
 **/

@RestController
public class TestAspectController {

    @Autowired
    TestAspectService testAspectService;

    @GetMapping("/testAspect")
    public String testAspect(){
        return testAspectService.doSomeThing("传个1");
    }
}
