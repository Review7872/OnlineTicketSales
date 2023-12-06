package online.fadai.car.service;


import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import online.fadai.car.mapper.CarMapper;
import online.fadai.car.mapper.RedisMapper;
import online.fadai.car.mapper.RouteInfoMapper;
import online.fadai.car.utils.SnowflakeIdGenerator;
import online.fadai.pojo.Car;
import online.fadai.pojo.carInfo.RouteInfo;
import online.fadai.pojo.carInfo.Seat;
import online.fadai.service.CarService;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.Redisson;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@DubboService
@Transactional
@Slf4j
public class CarServiceImpl implements CarService {
    @Resource
    private CarMapper carMapper;
    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Resource
    private RouteInfoMapper routeInfoService;
    @Resource
    private RedisMapper redisMapper;
    @Resource
    private Redisson redisson;

    @Override
    public List<Car> selectAll() {
        return carMapper.selectAll();
    }

    @Override
    public List<Car> selectAllByOpen(int open) {
        return carMapper.selectAllByOpen(open);
    }

    @Override
    public List<Car> selectAllByOpenAndCarNum(int open, String carNum) {
        return carMapper.selectAllByOpenAndCarNum(open, carNum);
    }

    @Override
    public List<Car> selectAllByRoute(String beginRoute, String endRoute, int open) {
        return carMapper.selectAllByRoute("-" + beginRoute, "-" + endRoute, open);
    }

    @Override
    public Car selectOne(long carId) {
        return carMapper.selectOne(carId);
    }

    @Override
    public long insertCar(List<Map<String, String>> routeAndTime, Map<String, Seat> seats, String carNum, int open, String openTime) {
        long carId = snowflakeIdGenerator.nextId();
        StringBuffer strRoute = new StringBuffer();
        routeAndTime.forEach(i -> strRoute.append("-").append(i.get("route")));
        carMapper.insertCar(carId, strRoute.toString(), carNum, open, openTime, JSON.toJSONString(seats));
        routeInfoService.insertCar(String.valueOf(carId), routeAndTime, seats);
        return carId;
    }

    @Override
    public int updateCarNum(String carNum, long carId) {
        return carMapper.updateCarNum(carNum, carId);
    }

    @Override
    public int updateOpen(int open, long carId) {
        return carMapper.updateOpen(open, carId);
    }

    @Override
    public List<Car> selectByCarNum(String carNum) {
        return carMapper.selectByCarNum(carNum);
    }

    @Override
    public int setRealTime(String carId, String route, String time) {
        return routeInfoService.setRealTime(carId, route, time);
    }

    @Override
    public int buyTicketSale(String carId, String beginRoute, String endRoute, String seatId, String cardId) {
        Map<String, RouteInfo> routeInfos = null;
        try {
            redisson.getReadWriteLock(carId).writeLock().lock(5, TimeUnit.SECONDS);
            Car car = carMapper.selectOne(Long.parseLong(carId));
            if (car == null) {
                throw new RuntimeException("未找到此列车");
            }
            String[] split = car.getRoute().split("-");
            routeInfos = new HashMap<>();
            for (String routeName : split) {
                routeInfos.put(routeName, redisMapper.getRedisData(carId, routeName));
            }
            boolean isTrue = false;
            for (String routeName : split) {
                if (routeName.equals(beginRoute)) {
                    isTrue = true;
                }
                if (isTrue) {
                    if (routeName.equals(endRoute)) {
                        break;
                    }
                    routeInfoService.setUser(carId, routeName, cardId, seatId);
                }
            }
            return 1;
        } catch (Exception e) {
            if (routeInfos != null) {
                routeInfos.forEach((m, n) -> redisMapper.setRedisData(carId, m, n));
            }
            throw new RuntimeException(e);
        } finally {
            redisson.getReadWriteLock(carId).writeLock().unlock();
        }
    }

    @Override
    public int buyTicketSaleBack(String carId, String beginRoute, String endRoute, String seatId) {
        Map<String, RouteInfo> routeInfos = null;
        try {
            redisson.getReadWriteLock(carId).writeLock().lock(5, TimeUnit.SECONDS);
            Car car = carMapper.selectOne(Long.parseLong(carId));
            if (car == null) {
                throw new RuntimeException("未找到此列车");
            }
            String[] split = car.getRoute().split("-");
            routeInfos = new HashMap<>();
            for (String routeName : split) {
                routeInfos.put(routeName, redisMapper.getRedisData(carId, routeName));
            }
            boolean isTrue = false;
            for (String routeName : split) {
                if (routeName.equals(beginRoute)) {
                    isTrue = true;
                }
                if (isTrue) {
                    if (routeName.equals(endRoute)) {
                        break;
                    }
                    routeInfoService.setUserBack(carId, routeName, seatId);
                }
            }
            return 1;
        } catch (Exception e) {
            if (routeInfos != null) {
                routeInfos.forEach((m, n) -> redisMapper.setRedisData(carId, m, n));
            }
            throw new RuntimeException(e);
        } finally {
            redisson.getReadWriteLock(carId).writeLock().unlock();
        }
    }

    @Override
    public RouteInfo selectRouteInfo(String carId, String route) {
        try {
            redisson.getReadWriteLock(carId).readLock().lock(5, TimeUnit.SECONDS);
            return routeInfoService.getRouteInfo(carId, route);
        } finally {
            redisson.getReadWriteLock(carId).readLock().unlock();
        }
    }

    @Override
    public String selectSeatInfo(String carId, String route, String seatId) {
        try {
            redisson.getReadWriteLock(carId).readLock().lock(5, TimeUnit.SECONDS);
            return routeInfoService.selectSeatInfo(carId, route, seatId);
        } finally {
            redisson.getReadWriteLock(carId).readLock().unlock();
        }
    }

    @Override
    public Map<String, Set<String>> selectNullSeat(String carId, String beginRoute, String endRoute) {
        try {
            redisson.getReadWriteLock(carId).readLock().lock(5, TimeUnit.SECONDS);
            Car car = carMapper.selectOne(Long.parseLong(carId));
            if (car == null) {
                throw new RuntimeException("未找到此列车");
            }
            String[] split = car.getRoute().split("-");
            Map<String, Set<String>> maps = new HashMap<>();
            Set<String> strings = car.getSeatInfo().keySet();
            strings.forEach(i -> maps.put(i, new HashSet<>()));
            boolean isTrue = false;
            for (String routeName : split) {
                if (routeName.equals(beginRoute)) {
                    isTrue = true;
                }
                if (isTrue) {
                    if (routeName.equals(endRoute)) {
                        break;
                    }
                    HashMap<String, Map<String, String>> seatInfo = routeInfoService.
                            getRouteInfo(carId, routeName).getSeatInfo();
                    seatInfo.forEach((m, n) -> {
                        Set<String> userInfo = maps.get(m);
                        userInfo.addAll(n.keySet());
                        maps.put(m, userInfo);
                    });
                }
            }
            return maps;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            redisson.getReadWriteLock(carId).readLock().unlock();
        }
    }
}
