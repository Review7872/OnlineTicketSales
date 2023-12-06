package online.fadai.buytickets.bean;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DeadQueueBean {
    public static final String REMINDER_QUEUE_NAME = "reminderQueue";
    public static final String DEAD_EXCHANGE = "deadExchange";
    public static final String DEAD_ROUTING_KEY = "deadRoutingKey";
    public static final String DEAD_QUEUE = "deadQueue";
    @Bean("reminderQueue")
    public Queue reminderQueue(){
        Map<String,Object> arguments=new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key",DEAD_ROUTING_KEY);
        //设置TTL 单位是ms
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(REMINDER_QUEUE_NAME).withArguments(arguments).build();
    }
    @Bean("deadQueue")
    public Queue deadQueue(){
        return new Queue(DEAD_QUEUE);
    }
    @Bean("deadExchange")
    public DirectExchange deadExchange(){
        return new DirectExchange(DEAD_EXCHANGE);
    }
    @Bean
    public Binding queueBBindingX(@Qualifier(DEAD_QUEUE) Queue queueB,
                                  @Qualifier(DEAD_EXCHANGE) DirectExchange xExchange){

        return BindingBuilder.bind(queueB).to(xExchange).with(DEAD_ROUTING_KEY);
    }
}
