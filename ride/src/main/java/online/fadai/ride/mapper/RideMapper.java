package online.fadai.ride.mapper;


import online.fadai.pojo.Ride;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RideMapper {
    @Select("")
    @Results(id = "rideRes", value = {
            @Result(id = true, column = "ride_id", property = "rideId"),
            @Result(column = "car_id", property = "carId"),
            @Result(column = "seat_id", property = "seatId"),
            @Result(column = "begin_route", property = "beginRoute"),
            @Result(column = "end_route", property = "endRoute"),
            @Result(column = "ride_stat", property = "rideStat")
    })
    Ride rideRes();

    @Select("""
            select ride_id,car_id,seat_id,begin_route,end_route,ride_stat from ride
            """)
    @ResultMap(value = "rideRes")
    List<Ride> selectAll();

    @Insert("""
            insert into ride(ride_id,car_id,seat_id,begin_route,end_route,ride_stat)values(#{rideId},#{carId},#{seatId},#{beginRoute},#{endRoute},#{rideStat})
                        """)
    int insert(Long rideId, Long carId, String seatId, String beginRoute, String endRoute, Integer rideStat);

    @Update("""
            update ride set ride_stat = #{rideStat} where ride_id = #{rideId}
            """)
    int updateRideStat(long rideId, Integer rideStat);

    @Select("""
            select ride_id,car_id,seat_id,begin_route,end_route,ride_stat from ride where ride_id = #{rideId}
            """)
    @ResultMap(value = "rideRes")
    Ride selectOne(long rideId);

    @Select("""
            select ride_id,car_id,seat_id,begin_route,end_route,ride_stat from ride where car_id = #{carId}
            """)
    @ResultMap(value = "rideRes")
    List<Ride> selectCar(long carId);

    @Delete("""
            delete from ride where ride_id = #{rideId}
            """)
    int deleteRide(long rideId);
}
