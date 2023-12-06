package online.fadai.service;


import online.fadai.pojo.Car;
import online.fadai.pojo.carInfo.RouteInfo;
import online.fadai.pojo.carInfo.Seat;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CarService {
    /**
     * 查询所有车辆信息
     *
     * @return 车辆信息
     */
    List<Car> selectAll();

    /**
     * 根据开启情况查询
     *
     * @param open 开启情况
     * @return 车辆信息
     */
    List<Car> selectAllByOpen(int open);

    /**
     * 根据开启情况与车牌号查询
     *
     * @param open   开启情况
     * @param carNum 车牌号
     * @return 车辆信息
     */
    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    /**
     * 根据起点终点查询
     *
     * @param beginRoute 起点
     * @param endRoute   终点
     * @param open       开启情况
     * @return 车辆信息
     */
    List<Car> selectAllByRoute(String beginRoute, String endRoute, int open);

    /**
     * 根据车次id准确查询
     *
     * @param carId 车次id
     * @return 车辆
     */
    Car selectOne(long carId);

    /**
     * 新增车辆
     *
     * @param routeAndTime 站点及其时间
     * @param seats        座位信息
     * @param carNum       车牌号
     * @param open         打开情况
     * @param openTime     发车时间
     * @return 车次id
     * 示例
     * {
     * "routeAndTime": [
     * {
     * "route": "蓬莱",
     * "time": "8.5"
     * },
     * {
     * "route": "烟台",
     * "time": "8.5"
     * },
     * {
     * "route": "郑州",
     * "time": "8.5"
     * },
     * {
     * "route": "西安",
     * "time": "8.5"
     * },
     * {
     * "route": "渭南",
     * "time": "8.5"
     * }
     * ],
     * "seats": {
     * "A": {
     * "money": 100,
     * "name": "A",
     * "total": 30
     * },
     * "B": {
     * "money": 80,
     * "name": "B",
     * "total": 50
     * },
     * "C": {
     * "money": 50,
     * "name": "C",
     * "total": 80
     * },
     * "D": {
     * "money": 30,
     * "name": "D",
     * "total": 100
     * }
     * },
     * "carNum": "carNum_28f170ae3601",
     * "open": 1,
     * "openTime": "8.2 12.00"
     * }
     */
    long insertCar(List<Map<String, String>> routeAndTime, Map<String, Seat> seats, String carNum, int open, String openTime);

    /**
     * 更新车牌号
     *
     * @param carNum 车牌号
     * @param carId  车次id
     * @return 成功/失败
     */
    int updateCarNum(String carNum, long carId);

    /**
     * 更新开启情况
     *
     * @param open  开启
     * @param carId 车次id
     * @return 成功/失败
     */
    int updateOpen(int open, long carId);

    /**
     * 根据车牌号查询
     *
     * @param carNum 车牌号
     * @return 车辆信息
     */
    List<Car> selectByCarNum(String carNum);

    /**
     * 设置到站时间
     *
     * @param carId 车次id
     * @param route 站点
     * @param time  时间
     * @return 成功/失败
     */
    int setRealTime(String carId, String route, String time);

    /**
     * 买票
     *
     * @param carId      车次id
     * @param beginRoute 起点
     * @param endRoute   终点
     * @param seatId     座位号
     * @param cardId     证件号
     * @return 成功/失败
     */
    int buyTicketSale(String carId, String beginRoute, String endRoute, String seatId, String cardId);

    /**
     * 退票
     *
     * @param carId      车次id
     * @param beginRoute 起点
     * @param endRoute   终点
     * @param seatId     座位号
     * @return 成功/失败
     */
    int buyTicketSaleBack(String carId, String beginRoute, String endRoute, String seatId);

    /**
     * 查询站点信息
     *
     * @param carId 车次id
     * @param route 站点
     * @return 信息
     */
    RouteInfo selectRouteInfo(String carId, String route);

    /**
     * 查询座位信息
     *
     * @param carId  车次id
     * @param route  站点
     * @param seatId 座位id
     * @return 证件号
     */
    String selectSeatInfo(String carId, String route, String seatId);

    /**
     * 查询已占座位
     *
     * @param carId      车次id
     * @param beginRoute 起点
     * @param endRoute   终点
     * @return 站点，已占座位
     */
    Map<String, Set<String>> selectNullSeat(String carId, String beginRoute, String endRoute);
}
