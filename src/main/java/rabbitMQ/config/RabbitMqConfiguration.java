package rabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitMqConfiguration
 * @Description
 * @Author wpj
 * @Date 2021/6/20 22:30
 **/

@Configuration
public class RabbitMqConfiguration {
    //1.声明fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        //param1:交换机名称 param2:是否持久化 param3:是否自动删除
        return new FanoutExchange("fanout_order_exchange",true,false);
    }
    //2.声明队列,  //例:sms.fanout.queue   email.fanout.queue
    @Bean
    public Queue smsQueus(){
        return new Queue("sms.fanout.queue",true); //queue名称,持久化
    }
    @Bean
    public Queue emailQueus(){
        return new Queue("email.fanout.queue",true); //queue名称,持久化
    }
    //3.完成交换机与队列的绑定关系
    @Bean
    public Binding smsBing(){
        return BindingBuilder.bind(smsQueus()).to(fanoutExchange());
    }
    @Bean
    public Binding emailBing(){
        return BindingBuilder.bind(emailQueus()).to(fanoutExchange());
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topic_Exchange",true,false);
    }

    @Bean
    public Queue smsTopicQueus(){  //队列和死信交换机作关联.
        Map<String,Object> args = new HashMap<>(12);
        args.put("x-message-ttl",5000); //单位默认毫秒  int 类型
        args.put("x-max-length",5); //最大长度5条
        args.put("x-dead-letter-exchange","dead_Exchange"); //将topic队列绑定上死信交换机
        args.put("x-dead-letter-routing-key","dead"); //私信队列key
        return new Queue("sms.topic.queue",true,false,false,args); //queue名称,持久化
    }
    @Bean
    public Binding smsTopicBing(){
        return  BindingBuilder.bind(smsTopicQueus()).to(topicExchange()).with("*.wechai.#");
//        return  BindingBuilder.bind(smsTopicQueus()).to(topicExchange()).with("sms");
    }

    @Bean
    public DirectExchange directExchange(){ //死信交换机 可以是任何模式的交换机
        return new DirectExchange("dead_Exchange",true,false);
    }
    @Bean
    public Queue deadQueue(){
        return new Queue("dead_queue",true);
    }
    @Bean
    public Binding deadBing(){//队列绑定死信交换机
        return  BindingBuilder.bind(deadQueue()).to(directExchange()).with("dead");
    }


}
