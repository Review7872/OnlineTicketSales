package online.fadai.buytickets.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import online.fadai.buytickets.bean.RabbitMQCallback;
import online.fadai.buytickets.exception.BuyTicketSaleBackException;
import online.fadai.buytickets.exception.BuyTicketSaleException;
import online.fadai.buytickets.exception.UpdateRideErrorException;
import online.fadai.buytickets.service.TicketSalesService;
import online.fadai.pojo.Order;
import online.fadai.pojo.Ride;
import online.fadai.pojo.User;
import online.fadai.service.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketSalesImpl implements TicketSalesService {
    @Resource(name = "payBackQueue")
    private Queue payBackQueue;
    @Resource(name = "reminderQueue")
    private Queue reminderQueue;
    @DubboReference
    private CarService carService;
    @DubboReference
    private OrderService orderService;
    @DubboReference
    private RideService rideService;
    @DubboReference
    private PayService payService;
    @DubboReference
    private UserService userService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RabbitMQCallback rabbitMQCallback;
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(rabbitMQCallback);
    }

    @Override
    public int buyTicketSale(String carId, String beginRoute, String endRoute, String seatId, String cardId,String phone) {
        Integer carInfo = null;
        Long rideId = null;
        Long payId = null;
        Long orderId = null;
        try {
            carInfo = carService.buyTicketSale(carId, beginRoute, endRoute, seatId, cardId);
            if (carInfo != 1){
                throw new RuntimeException("购票失败");
            }
            rideId = rideService.createRide(Long.parseLong(carId), seatId, beginRoute, endRoute);
            if (rideId < 1){
                throw new RuntimeException("新建乘坐单失败");
            }
            payId = payService.createPay();
            if (payId < 1){
                throw new RuntimeException("新建支付单失败");
            }
            int i = payService.updatePayWay(payId, 2);
            if (i != 1){
                throw new RuntimeException("修改支付方式失败");
            }
            orderId = orderService.createOrder(cardId,rideId,payId,phone);
            if (orderId < 1){
                throw new RuntimeException("新建订单失败");
            }
            rabbitTemplate.convertAndSend(reminderQueue.getName(),phone);
            return 1;
        }catch (Exception e){
            log.error(e.getMessage());
            if (carInfo != null){
                carService.buyTicketSaleBack(carId,beginRoute,endRoute,seatId);
            }
            if (rideId != null){
                rideService.deleteRide(rideId);
            }
            if (payId != null){
                payService.deletePay(payId);
            }
            if (orderId != null) {
                orderService.deleteOrder(orderId);
            }
            throw new BuyTicketSaleException();
        }
    }
    @Override
    public int buyTicketSaleBack(long orderId) {
        Order order = orderService.selectOne(orderId);
        if (order == null){
            throw new RuntimeException("未找到此订单");
        }
        Ride ride = rideService.selectOne(order.getRideId());
        if (ride == null){
            throw new RuntimeException("未找到乘坐单");
        }
        try {
            int i = carService.buyTicketSaleBack(String.valueOf(ride.getCarId()), ride.getBeginRoute(), ride.getEndRoute(), ride.getSeatId());
            if (i != 1){
                throw new RuntimeException("退票失败");
            }
            int i1 = rideService.updateRideStat(order.getRideId(), 2);
            if (i1 != 1){
                throw new UpdateRideErrorException();
            }
            rabbitTemplate.convertAndSend(payBackQueue.getName(),order.getPhone());
            return 1;
        } catch (UpdateRideErrorException e) {
            log.error("更新Ride退款失败 rideId:{}",order.getRideId());
            return 1;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BuyTicketSaleBackException();
        }
    }

    @Override
    public Object selectOrder(String username) {
        User user = userService.selectOne(username);
        if (user == null){
            throw new RuntimeException("未找到此用户");
        }
        return orderService.selectByCardId(user.getCardId());
    }
    @Override
    public int payOrder(long orderId){
        Order order = orderService.selectOne(orderId);
        if (order == null){
            throw new RuntimeException("未找到该订单");
        }
        return payService.updatePayStat(order.getPayId(),1);
    }
    @Override
    public int payWay(long orderId,int payWay){
        Order order = orderService.selectOne(orderId);
        if (order == null){
            throw new RuntimeException("未找到该订单");
        }
        return payService.updatePayWay(order.getPayId(),payWay);
    }
}
