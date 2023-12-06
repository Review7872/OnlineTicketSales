package online.fadai.car.mapper;

import online.fadai.pojo.carInfo.RouteInfo;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisMapper {


    void setRedisData(String key, String route, RouteInfo routeInfo);

    RouteInfo getRedisData(String key, String route);

    RedisTemplate<String, String> getRedisTemplate();
}
