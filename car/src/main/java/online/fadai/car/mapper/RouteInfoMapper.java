package online.fadai.car.mapper;

import online.fadai.pojo.carInfo.RouteInfo;
import online.fadai.pojo.carInfo.Seat;

import java.util.List;
import java.util.Map;

public interface RouteInfoMapper {
    /**
     * 新增车辆
     *
     * @param carId        车次id
     * @param routeAndTime 站点和时间
     * @return 1/0 成功/失败
     */
    int insertCar(String carId, List<Map<String, String>> routeAndTime, Map<String, Seat> seats);

    /**
     * 设置用户证件号
     *
     * @param carId  车次id
     * @param route  站点
     * @param cardId 证件号
     * @param seatId 座位id
     * @return 1/0 成功/失败
     */
    int setUser(String carId, String route, String cardId, String seatId);

    /**
     * 清除用户证件号
     *
     * @param carId  车次id
     * @param route  站点
     * @param seatId 座位id
     * @return 1/0 成功/失败
     */
    int setUserBack(String carId, String route, String seatId);

    /**
     * 查询用户
     *
     * @param carId  车次id
     * @param route  站点
     * @param seatId 座位id
     * @return * / "" / null 证件号/没人/失败
     */
    String selectSeatInfo(String carId, String route, String seatId);

    /**
     * 设置到站时间
     *
     * @param carId 车次id
     * @param route 站点
     * @param time  时间
     * @return 1/0 成功/失败
     */
    int setRealTime(String carId, String route, String time);

    /**
     * 查询站点信息
     *
     * @param carId 车次id
     * @param route 站点
     * @return * / null  车次信息/失败
     */
    RouteInfo getRouteInfo(String carId, String route);
}
