package online.fadai.pay.service;

import jakarta.annotation.Resource;
import online.fadai.pay.mapper.PayMapper;
import online.fadai.pay.utils.SnowflakeIdGenerator;
import online.fadai.pojo.Pay;
import online.fadai.service.PayService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DubboService
@Transactional
public class PayServiceImpl implements PayService {
    @Resource
    private PayMapper payMapper;
    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Resource
    private SimpleDateFormat simpleDateFormat;

    @Override
    public long createPay() {
        long payId = snowflakeIdGenerator.nextId();
        Integer i = payMapper.insertPay(payId);
        if (i == 0) {
            return 0;
        }
        return payId;
    }

    @Override
    public int updatePayWay(long payId, int payWay) {
        return payMapper.updatePayWay(payWay, payId);
    }

    @Override
    public int updatePayStat(long payId, int payStat) {
        return payMapper.updatePayStat(payStat, payId, simpleDateFormat.format(new Date()));
    }

    @Override
    public Pay selectOne(long payId) {
        return payMapper.selectByPayId(payId);
    }

    @Override
    public List<Pay> selectByPayStat(int payStat) {
        return payMapper.selectByPayStat(payStat);
    }

    @Override
    public List<Pay> selectByPayWay(int payWay) {
        return payMapper.selectByPayWay(payWay);
    }

    @Override
    public List<Pay> selectByTwo(int payWay, int payStat) {
        return payMapper.selectByPayWayAndPayStat(payWay, payStat);
    }
    @Override
    public int deletePay(long payId){
        return payMapper.deletePay(payId);
    }
}
