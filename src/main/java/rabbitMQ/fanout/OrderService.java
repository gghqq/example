package rabbitMQ.fanout;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName OrderService
 * @Description
 * 生产订单 存放到队列当中
 * @Author wpj
 * @Date 2021/6/20 22:26
 **/

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void makeOrder(String userId,String productId,int num){
        //1.todo 根据商品Id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        //3.通过MQ来完成分发 param1:交换机 param2:路由key/交换机名称 param3:消息内容
        rabbitTemplate.convertAndSend("fanout_order_exchange","",orderId);
    }
    public void makeOrderTopic(String userId,String productId,int num){
        //1.todo 根据商品Id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        //3.通过MQ来完成分发 param1:交换机 param2:路由key/交换机名称 param3:消息内容
        //#.sms.#   *.wechai.#  *必须有一个
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() { //给消息设置过期时间
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            }
        };

        rabbitTemplate.convertAndSend("topic_Exchange","com.wechai.sms",orderId);
        rabbitTemplate.convertAndSend("direct_Exchange","sms",orderId,messagePostProcessor);
    }
}
