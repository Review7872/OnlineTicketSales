package online.fadai.order.mapper;

import online.fadai.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface OrderMapper {
    @Select("")
    @Results(id = "orderRes", value = {
            @Result(id = true, column = "order_id", property = "orderId"),
            @Result(column = "card_id", property = "cardId"),
            @Result(column = "ride_id", property = "rideId"),
            @Result(column = "pay_id", property = "payId"),
            @Result(column = "order_time", property = "orderTime"),
            @Result(column = "phone",property = "phone")
    })
    Order result();

    @Select("""
            select order_id,card_id,ride_id,pay_id,order_time,phone from order_t order by order_time desc
            """)
    @ResultMap("orderRes")
    List<Order> selectAllOrder();

    @Select("""
            select order_id,card_id,ride_id,pay_id,order_time,phone from order_t where card_id = #{cardId} order by order_time desc
            """)
    @ResultMap("orderRes")
    List<Order> selectOrderByCardId(String cardId);

    @Insert("""
            insert into order_t(order_id,card_id,ride_id,pay_id,order_time,phone) values(#{orderId},#{cardId},#{rideId},#{payId},#{orderTime},#{phone})
            """)
    Integer insertOrder(Long orderId, String cardId, Long rideId, long payId, String orderTime,String phone);

    @Update("""
            update order_t set ride_id=#{rideId} where order_id = #{orderId}
            """)
    Integer updateOrder(long rideId, Long orderId);

    @Update("""
            update order_t set pay_id=#{payId} where order_id = #{orderId}
            """)
    Integer updatePay(long payId, Long orderId);

    @Select("""
            select order_id,card_id,ride_id,pay_id,order_time,phone from order_t where order_id = #{orderId}
            """)
    @ResultMap("orderRes")
    Order selectOne(long orderId);
    @Delete("""
            delete from order_t where order_id = #{orderId}
            """)
    int deleteOrder(long orderId);
}
