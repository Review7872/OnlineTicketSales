package online.fadai.service;

import online.fadai.pojo.Order;

import java.util.List;

public interface OrderService {
    /**
     * 新增订单
     *
     * @param cardId 证件号
     * @param rideId 座位单号
     * @return 订单号
     */
    long createOrder(String cardId, long rideId,long payId,String phone);

    /**
     * 更新支付单
     *
     * @param payId   支付单号
     * @param orderId 订单号
     * @return 成功/失败
     */
    int updatePayId(long payId, long orderId);

    /**
     * 查询单个订单
     *
     * @param orderId 订单号
     * @return 单个订单
     */
    Order selectOne(long orderId);

    /**
     * 根据证件号查询
     *
     * @param cardId 证件号
     * @return 订单信息
     */
    List<Order> selectByCardId(String cardId);

    int deleteOrder(long orderId);
}
