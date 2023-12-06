package online.fadai.order.service;

import jakarta.annotation.Resource;
import online.fadai.order.mapper.OrderMapper;
import online.fadai.order.utils.SnowflakeIdGenerator;
import online.fadai.pojo.Order;
import online.fadai.service.OrderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DubboService
@Transactional
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Resource
    private SimpleDateFormat simpleDateFormat;

    @Override
    public long createOrder(String cardId, long rideId,long payId,String phone) {
        long orderId = snowflakeIdGenerator.nextId();
        Integer i = orderMapper.insertOrder(orderId, cardId, rideId, payId, simpleDateFormat.format(new Date()),phone);
        if (i == 0) {
            return 0;
        }
        return orderId;
    }

    @Override
    public int updatePayId(long payId, long orderId) {
        return orderMapper.updatePay(payId, orderId);
    }

    @Override
    public Order selectOne(long orderId) {
        return orderMapper.selectOne(orderId);
    }

    @Override
    public List<Order> selectByCardId(String cardId) {
        return orderMapper.selectOrderByCardId(cardId);
    }
    @Override
    public int deleteOrder(long orderId){
        return orderMapper.deleteOrder(orderId);
    }

}
