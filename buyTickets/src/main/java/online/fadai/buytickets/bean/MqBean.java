package online.fadai.buytickets.bean;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
