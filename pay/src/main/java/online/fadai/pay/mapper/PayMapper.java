package online.fadai.pay.mapper;

import online.fadai.pojo.Pay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PayMapper {
    @Select("")
    @Results(id = "payRes", value = {
            @Result(id = true, column = "pay_id", property = "payId"),
            @Result(column = "pay_way", property = "payWay"),
            @Result(column = "pay_stat", property = "payStat"),
            @Result(column = "pay_time", property = "payTime")
    })
    Pay payRes();

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay
            """)
    @ResultMap("payRes")
    List<Pay> selectAll();

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_stat = #{payStat}
            """)
    @ResultMap("payRes")
    List<Pay> selectByPayStat(int payStat);

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_way = #{payWay}
            """)
    @ResultMap("payRes")
    List<Pay> selectByPayWay(int payWay);

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_way = #{payWay} and pay_stat = #{payStat}
            """)
    @ResultMap("payRes")
    List<Pay> selectByPayWayAndPayStat(int payWay, int payStat);

    @Select("""
            select pay_id,pay_way,pay_stat,pay_time from pay where pay_id = #{payId}
            """)
    @ResultMap("payRes")
    Pay selectByPayId(long payId);

    @Insert("""
            insert into pay(pay_id) values(#{payId})
            """)
    Integer insertPay(long payId);

    @Update("""
            update pay set pay_stat = #{payStat},pay_time = #{payTime} where pay_id = #{payId}
            """)
    Integer updatePayStat(int payStat, long payId, String payTime);

    @Update("""
            update pay set pay_way = #{payWay} where pay_id = #{payId}
            """)
    Integer updatePayWay(int payWay, long payId);

    @Update("""
            update pay set pay_way=#{payWay},pay_stat = #{payStat} where pay_id = #{payId}
            """)
    Integer updatePayWayAndPayStat(int payWay, int payStat, long payId);
    @Delete("""
            delete from pay where pay_id = #{payId}
            """)
    int deletePay(long payId);
}
