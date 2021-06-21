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
@RabbitListener(queues = "sms.fanout.queue")
public class SMSConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("sms接收到的消息: " + message);
    }
}
