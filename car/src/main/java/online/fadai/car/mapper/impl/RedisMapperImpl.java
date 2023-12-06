package online.fadai.car.mapper.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import online.fadai.car.mapper.RedisMapper;
import online.fadai.pojo.carInfo.RouteInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisMapperImpl implements RedisMapper {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void setRedisData(String key, String route, RouteInfo routeInfo) {
        redisTemplate.opsForHash().put(key, route, JSON.toJSONString(routeInfo));
    }

    @Override
    public RouteInfo getRedisData(String key, String route) {
        return JSON.parseObject((String) redisTemplate.opsForHash().get(key, route), RouteInfo.class);
    }

    @Override
    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }
}
