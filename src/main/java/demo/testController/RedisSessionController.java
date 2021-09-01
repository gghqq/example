package demo.testController;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RedisSessionController
 * @Description:
 * 使用sSpring Session使得基于Redis的Session共享应⽤起来⾮常之简单
 * 启动类使用 @EnableRedisHttpSession 注解
 * @Author wpj
 * @Date 2021/8/29 19:56
 **/
@RestController
public class RedisSessionController {

    @GetMapping("/redis/session")
    public String getRedisSession(HttpServletRequest request){
        Object admin = request.getSession().getAttribute("admin");
        if (null == admin) {
            request.getSession().setAttribute("admin","admin");
            return "设置session成功";
        }
        return admin.toString();
    }
}
