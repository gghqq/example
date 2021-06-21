package rabbitMQ.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName SMSConstumer
 * @Description
 * 消费者
 * @Author wpj
 * @Date 2021/6/20 22:47
 **/
@Component
//@RabbitListener(queues = "email.fanout.queue")
@RabbitListener(queues = "sms.topic.queue")
//@RabbitListener(queues = "sms.direct.queue")
public class EmailConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("sms接收到的消息: " + message);
    }
}
