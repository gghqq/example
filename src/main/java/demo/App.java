package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName App
 * @Description
 * @Author agan
 * @Date 2021/5/10 22:39
 **/
//@SpringBootApplication(scanBasePackages ={"canal","elasticSearch","rabbitMQ.md","elastic_job"})
@SpringBootApplication(scanBasePackages ={"elasticSearch"})
@EnableTransactionManagement
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
//        app.setDefaultProperties(Collections
//                .singletonMap("server.port", "8083"));
        app.run(args);
    }


}
