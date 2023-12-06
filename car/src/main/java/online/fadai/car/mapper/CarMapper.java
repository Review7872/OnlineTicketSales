package online.fadai.car.mapper;

import online.fadai.pojo.Car;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarMapper {
    @Select("")
    @Results(id = "carRes", value = {
            @Result(id = true, column = "car_id", property = "carId"),
            @Result(column = "route", property = "route"),
            @Result(column = "car_num", property = "carNum"),
            @Result(column = "open", property = "open"),
            @Result(column = "open_time", property = "openTime"),
            @Result(column = "seatInfo", property = "seat")
    })
    Car carRes();

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car
            """)
    @ResultMap("carRes")
    List<Car> selectAll();

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car where open = #{open}
            """)
    @ResultMap("carRes")
    List<Car> selectAllByOpen(int open);

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car where open = #{open} and car_num like concat('%',#{carNum},'%')
            """)
    @ResultMap("carRes")
    List<Car> selectAllByOpenAndCarNum(int open, String carNum);

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car where route like concat('%',#{beginRoute},'%',#{endRoute},'%') and open = #{open}
            """)
    @ResultMap("carRes")
    List<Car> selectAllByRoute(String beginRoute, String endRoute, int open);

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car where car_id = #{carId}
            """)
    @ResultMap("carRes")
    Car selectOne(long carId);

    @Insert("""
            insert into car values(#{carId},#{route},#{carNum},#{open},#{openTime},#{seats})
            """)
    int insertCar(long carId, String route, String carNum, int open, String openTime, String seats);

    @Update("""
            update car set route = #{route} where car_id = #{carId}
            """)
    int updateRoute(String route, long carId);

    @Update("""
            update car set car_num = #{carNum} where car_id = #{carId}
            """)
    int updateCarNum(String carNum, long carId);

    @Update("""
            update car set open = #{open} where car_id = #{carId}
            """)
    int updateOpen(int open, long carId);

    @Select("""
            select car_id,route,car_num,open,open_time,seat_info from car where car_num like concat('%',#{carNum},'%')
            """)
    @ResultMap("carRes")
    List<Car> selectByCarNum(String carNum);

    @Update("""
            update car set seat_info = #{json} where car_id = #{carId}
            """)
    int updateSeat(long carId, String json);
}
