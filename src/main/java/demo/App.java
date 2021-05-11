package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Collections;

/**
 * @ClassName App
 * @Description
 * @Author agan
 * @Date 2021/5/10 22:39
 **/

@SpringBootApplication
public class App  extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);

    }


}
