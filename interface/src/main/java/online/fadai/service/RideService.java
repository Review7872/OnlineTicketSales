package online.fadai.service;

import online.fadai.pojo.Ride;

import java.util.List;

public interface RideService {
    /**
     * 创建乘车单
     *
     * @param carId      车次id
     * @param seatId     座位id
     * @param beginRoute 起点
     * @param endRoute   终点
     * @return 乘车单号
     */
    long createRide(long carId, String seatId, String beginRoute, String endRoute);

    /**
     * 更新乘车状态
     *
     * @param rideId   乘车单号
     * @param rideStat 乘车状态
     * @return 成功/失败
     */
    int updateRideStat(long rideId, int rideStat);

    /**
     * 查询单个
     *
     * @param rideId 乘车单号
     * @return 单个乘车信息
     */
    Ride selectOne(long rideId);

    /**
     * 根据乘车单号查询
     *
     * @param carId 乘车单号
     * @return 乘车信息
     */
    List<Ride> selectByCarId(long carId);

    int deleteRide(long rideId);
}
