package online.fadai.service;

import online.fadai.pojo.Pay;

import java.util.List;

public interface PayService {
    /**
     * 创建支付单
     *
     * @return 支付单号
     */
    long createPay();

    /**
     * 更新支付方式
     *
     * @param payId  支付单号
     * @param payWay 支付方式
     * @return 成功/失败
     */
    int updatePayWay(long payId, int payWay);

    /**
     * 更新支付状态
     *
     * @param payId   支付单号
     * @param payStat 支付状态
     * @return 成功/失败
     */
    int updatePayStat(long payId, int payStat);

    /**
     * 查询单个支付单
     *
     * @param payId 支付单号
     * @return 支付单
     */
    Pay selectOne(long payId);

    /**
     * 根据支付状态查询
     *
     * @param payStat 支付状态
     * @return 支付信息
     */
    List<Pay> selectByPayStat(int payStat);

    /**
     * 根据支付方式查询
     *
     * @param payWay 支付方式
     * @return 支付信息
     */
    List<Pay> selectByPayWay(int payWay);

    /**
     * 双查
     *
     * @param payWay  支付方式
     * @param payStat 支付状态
     * @return 支付信息
     */
    List<Pay> selectByTwo(int payWay, int payStat);

    int deletePay(long payId);
}
