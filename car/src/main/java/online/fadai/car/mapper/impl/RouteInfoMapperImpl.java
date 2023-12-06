package online.fadai.car.mapper.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import online.fadai.car.mapper.RedisMapper;
import online.fadai.car.mapper.RouteInfoMapper;
import online.fadai.pojo.carInfo.RouteInfo;
import online.fadai.pojo.carInfo.Seat;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RouteInfoMapperImpl implements RouteInfoMapper {
    @Resource
    private RedisMapper redisMapper;

    /**
     * 新增车辆
     *
     * @param carId        车次id
     * @param routeAndTime 站点和时间
     * @return 1/0 成功/失败
     */
    @Override
    public int insertCar(String carId, List<Map<String, String>> routeAndTime, Map<String, Seat> seats) {
        try {
            HashMap<String, Map<String, String>> map = new HashMap<>();
            seats.forEach((m, n) -> map.put(m, new HashMap<>(n.getTotal())));
            routeAndTime.forEach(i -> redisMapper.setRedisData(carId, i.get("route"),
                    new RouteInfo(i.get("route"), i.get("time"), null, map)));
            return 1;
        } catch (Exception e) {
            redisMapper.getRedisTemplate().delete(carId);
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 设置用户证件号
     *
     * @param carId  车次id
     * @param route  站点
     * @param cardId 证件号
     * @param seatId 座位id
     * @return 1/0 成功/失败
     */
    @Override
    public int setUser(String carId, String route, String cardId, String seatId) {
        try {
            RouteInfo seatData = redisMapper.getRedisData(carId, route);
            if (seatData == null) {
                throw new Exception("未找到此列车的此站点");
            }
            String s = seatData.getSeatInfo().get(seatId.substring(0, 1))
                    .get(seatId.substring(1));
            if (s == null || s.isEmpty()) {
                seatData.getSeatInfo().get(seatId.substring(0, 1))
                        .put(seatId.substring(1), cardId);
            } else {
                throw new Exception("此座位不为空");
            }
            redisMapper.setRedisData(carId, route, seatData);
            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 清除用户证件号
     *
     * @param carId  车次id
     * @param route  站点
     * @param seatId 座位id
     * @return 1/0 成功/失败
     */
    @Override
    public int setUserBack(String carId, String route, String seatId) {
        try {
            RouteInfo seatData = redisMapper.getRedisData(carId, route);
            if (seatData == null) {
                throw new Exception("未找到此列车的此站点");
            }
            seatData.getSeatInfo().get(seatId.substring(0, 1))
                    .remove(seatId.substring(1));
            redisMapper.setRedisData(carId, route, seatData);
            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 查询用户
     *
     * @param carId  车次id
     * @param route  站点
     * @param seatId 座位id
     * @return * / "" / null 证件号/没人/失败
     */
    @Override
    public String selectSeatInfo(String carId, String route, String seatId) {
        try {
            RouteInfo seatData = redisMapper.getRedisData(carId, route);
            if (seatData == null) {
                throw new Exception("未找到此列车的此站点");
            }
            String s = seatData.getSeatInfo().get(seatId.substring(0, 1))
                    .get(seatId.substring(1));
            if (s == null || s.isEmpty()) {
                return "";
            }
            return s;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 设置到站时间
     *
     * @param carId 车次id
     * @param route 站点
     * @param time  时间
     * @return 1/0 成功/失败
     */
    @Override
    public int setRealTime(String carId, String route, String time) {
        try {
            RouteInfo seatData = redisMapper.getRedisData(carId, route);
            if (seatData == null) {
                throw new Exception("未找到此列车的此站点");
            }
            seatData.setRealTime(time);
            redisMapper.setRedisData(carId, route, seatData);
            return 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 查询站点信息
     *
     * @param carId 车次id
     * @param route 站点
     * @return * / null  车次信息/失败
     */
    @Override
    public RouteInfo getRouteInfo(String carId, String route) {
        try {
            RouteInfo seatData = redisMapper.getRedisData(carId, route);
            if (seatData == null) {
                throw new Exception("未找到此列车的此站点");
            }
            return seatData;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
