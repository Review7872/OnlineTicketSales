package online.fadai.buytickets.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import online.fadai.buytickets.bean.RabbitMQCallback;
import online.fadai.buytickets.exception.AuthErrorException;
import online.fadai.buytickets.exception.CreateAccountFailedException;
import online.fadai.buytickets.service.AccountService;
import online.fadai.buytickets.utils.JwtUtil;
import online.fadai.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountImpl implements AccountService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource(name = "usernameQueue")
    private Queue queue;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @DubboReference
    private UserService userService;
    @Resource
    private RabbitMQCallback rabbitMQCallback;
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(rabbitMQCallback);
    }
    public static final String USER_AUTH = "1";

    @Override
    public int createUserAccount(String username, String password, String phone) {
        int insert = userService.insert(username, password, phone, USER_AUTH);
        if (insert != 1) {
            throw new CreateAccountFailedException();
        }
        rabbitTemplate.convertAndSend(queue.getName(), username);
        return 1;
    }

    @Override
    public int usernameCanUse(String username) {
        Boolean aBoolean = redisTemplate.opsForSet().isMember("usernameSet", username);
        if (Boolean.TRUE.equals(aBoolean)) {
            return 1;
        }else if (Boolean.FALSE.equals(aBoolean)){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public String getUserAuth(String jwt) {
        Claims claimsFromJwt = JwtUtil.getClaimsFromJwt(jwt);
        Boolean b = redisTemplate.hasKey("jwt:" + claimsFromJwt.get("username"));
        if (Boolean.TRUE.equals(b)) {
            return claimsFromJwt.get("auth").toString();
        } else {
            throw new AuthErrorException();
        }
    }

}
