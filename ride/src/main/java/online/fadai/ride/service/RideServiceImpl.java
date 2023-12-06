package online.fadai.ride.service;

import jakarta.annotation.Resource;
import online.fadai.pojo.Ride;
import online.fadai.ride.mapper.RideMapper;
import online.fadai.ride.utils.SnowflakeIdGenerator;
import online.fadai.service.RideService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DubboService
@Transactional
public class RideServiceImpl implements RideService {
    @Resource
    private RideMapper rideMapper;
    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public long createRide(long carId, String seatId, String beginRoute, String endRoute) {
        long rideId = snowflakeIdGenerator.nextId();
        int i = rideMapper.insert(rideId, carId, seatId, beginRoute, endRoute, 0);
        if (i == 0) {
            return 0;
        }
        return rideId;
    }

    @Override
    public int updateRideStat(long rideId, int rideStat) {
        return rideMapper.updateRideStat(rideId, rideStat);
    }

    @Override
    public Ride selectOne(long rideId) {
        return rideMapper.selectOne(rideId);
    }

    @Override
    public List<Ride> selectByCarId(long carId) {
        return rideMapper.selectCar(carId);
    }
    @Override
    public int deleteRide(long rideId){
        return rideMapper.deleteRide(rideId);
    }
}
