package online.fadai.messagequeue.bean;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqBean {
    public static final String USERNAME_QUEUE_NAME = "usernameQueue";
    public static final String PAY_QUEUE_NAME = "payBackQueue";
    @Bean("usernameQueue")
    public Queue usernameQueue(){
        return new Queue(USERNAME_QUEUE_NAME);
    }
    @Bean("payBackQueue")
    public Queue payBackQueue(){
        return new Queue(PAY_QUEUE_NAME);
    }
}
