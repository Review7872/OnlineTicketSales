package online.fadai.buytickets.controller.user;

import jakarta.annotation.Resource;
import online.fadai.buytickets.service.TicketSalesService;
import online.fadai.pojo.Order;
import online.fadai.pojo.requestBody.TicketSale;
import online.fadai.service.CarService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "/*")
public class UserController {
    @Resource
    private TicketSalesService ticketSalesService;
    @DubboReference
    private CarService carService;

    /**
     * 查车辆
     * @param beginRoute
     * @param endRoute
     * @return
     */
    @GetMapping("selectCar")
    public Object selectCar(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                            String beginRoute, String endRoute) {
        return carService.selectAllByRoute(beginRoute, endRoute, 1);
    }

    /**
     * 买票
     * @param ticketSale
     * @return
     */
    @PostMapping("buyTicketSale")
    public Object buyTicketSale(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                                @RequestBody TicketSale ticketSale) {
        return ticketSalesService.buyTicketSale(ticketSale.getCarId(), ticketSale.getBeginRoute(),
                ticketSale.getEndRoute(), ticketSale.getSeatId(), ticketSale.getCardId(), ticketSale.getPhone());
    }

    /**
     * 退票
     * @param order
     * @return
     */
    @PostMapping("buyTicketSaleBack")
    public Object buyTicketSaleBack(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                                    @RequestBody Order order){
        return ticketSalesService.buyTicketSaleBack(order.getOrderId());
    }

    /**
     * 查询
     * @param username
     * @return
     */
    @GetMapping("selectOrder")
    public Object selectOrder(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                              String username){
        return ticketSalesService.selectOrder(username);
    }

    /**
     * 付款
     * @param order
     * @return
     */
    @PostMapping("payOrder")
    public Object payOrder(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                           @RequestBody Order order){
        return ticketSalesService.payOrder(order.getOrderId());
    }

    /**
     * 查询车辆信息
     * @param carId
     * @return
     */
    @GetMapping("selectCarInfo")
    public Object selectCarInfo(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                                long carId){
        return carService.selectOne(carId);
    }
}
