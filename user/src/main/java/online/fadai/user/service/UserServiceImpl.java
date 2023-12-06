package online.fadai.user.service;

import jakarta.annotation.Resource;
import online.fadai.pojo.User;
import online.fadai.service.UserService;
import online.fadai.user.mapper.UserMapper;
import online.fadai.user.utils.JwtUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@DubboService
@Transactional
public class UserServiceImpl implements UserService {
    private static final long JWT_EXPIRATION = 1;
    private static final long JWT_EXPIRATION_LONG = 7;
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringEncryptor encryptor;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getJWT(String username, String password) {
        User user = userMapper.selectUsernameAndPasswordAuth(username);
        if (!password.equals(encryptor.decrypt(user.getPassword()))) {
            throw new RuntimeException("密码错误");
        }
        String jwt = JwtUtil.generateJwtToken(user.getUsername(), user.getAuth());
        redisTemplate.opsForValue().set("jwt:" + user.getUsername(), jwt, JWT_EXPIRATION, TimeUnit.HOURS);
        return jwt;
    }

    @Override
    public String getJWTLong(String username, String password) {
        User user = userMapper.selectUsernameAndPasswordAuth(username);
        if (!password.equals(encryptor.decrypt(user.getPassword()))) {
            throw new RuntimeException("密码错误");
        }
        String jwt = JwtUtil.generateJwtToken(user.getUsername(), user.getAuth());
        redisTemplate.opsForValue().set("jwt:" + user.getUsername(), jwt, JWT_EXPIRATION_LONG, TimeUnit.DAYS);
        return jwt;
    }

    @Override
    public int insert(String username, String password, String phone, String auth) {

        return userMapper.insert(username, encryptor.encrypt(password), phone, auth);
    }

    @Override
    public User selectOne(String username) {
        User user = userMapper.selectOne(username);
        return new User(user.getUsername(), encryptor.decrypt(user.getPassword()), user.getPhone(),
                user.getAuth(), user.getPhoto(), user.getSex(), user.getAge(), user.getCardId());
    }

    @Override
    public int updatePhoto(String username, String phone) {
        return userMapper.updatePhoto(username, phone);
    }

    @Override
    public int updateSex(String username, int sex) {
        return userMapper.updateSex(username, sex);
    }

    @Override
    public int updateAge(String username, int age) {
        return userMapper.updateAge(username, age);
    }

    @Override
    public int updateCardId(String username, long cardId) {
        return userMapper.updateCardId(username, cardId);
    }

    @Override
    public int reReg(String phone, String password, String username) {
        return userMapper.reReg(phone, encryptor.encrypt(password), username);
    }

}
