package online.fadai.messagequeue.mqListen;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AddUsername {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @RabbitListener(queues = "usernameQueue")
    public void addUser(Message message ){
        Long add = redisTemplate.opsForSet().add("usernameSet", new String(message.getBody()));
        if (add==null||add!=1){
            log.error("redis 插入用户名失败 ->{}",new String(message.getBody()));
            throw new RuntimeException();
        }
    }
    @RabbitListener(queues = "payBackQueue")
    public void payBack(Message message){
        log.info("{} 已经退款",new String(message.getBody()));
    }
    @RabbitListener(queues = "deadQueue")
    public void reminder(Message message){
        log.info("{} 已经短信提醒",new String(message.getBody()));
    }
}
