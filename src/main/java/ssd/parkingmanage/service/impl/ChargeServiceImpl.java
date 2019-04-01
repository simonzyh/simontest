package parkingmanage.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import parkingmanage.constants.CartType;
import parkingmanage.model.ChargeRecordModel;
import parkingmanage.model.ChargeTypeModel;
import parkingmanage.model.ParkingRecordModel;
import parkingmanage.repository.ChargeRecordRepository;
import parkingmanage.repository.ChargeTypeRepository;
import parkingmanage.repository.ParkingRecordRepository;
import parkingmanage.service.ChargeService;
import parkingmanage.vo.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 计费服务
 * 负责计费单生产，支付回掉，金额统计
 */
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    ChargeTypeRepository chargeTypeRepository;
    @Autowired
    ChargeRecordRepository chargeRecordRepository;
    @Autowired
    ParkingRecordRepository parkingRecordRepository;

    /**
     * 出场时
     * 1 幂等操作， 如果已经生产了计费单则直接返回
     * 2 提交计费根据停车记录生成计费信息
     *
     * @param parkingRecordModel 当前计费记录对应的停车记录
     */
    @Override
    @Transactional
    public ChargeRecordModel submit(ParkingRecordModel parkingRecordModel) {
        //如果已经有计费单则直接返回
        ChargeRecordModel chargeRecordModel = chargeRecordRepository.getByParkingRecordId(parkingRecordModel.getId());
        if (null != chargeRecordModel) {
            return chargeRecordModel;
        }
        chargeRecordModel = new ChargeRecordModel();
        chargeRecordModel.setParkingRecordModel(parkingRecordModel);
        //设置计费类型
        ChargeTypeModel chargeTypeModel = filterChargeType(parkingRecordModel.getCardNo(), parkingRecordModel.getCartType());
        chargeRecordModel.setChargeTypeModel(chargeTypeModel);
        chargeRecordModel.setStatus((short) 0);
        //应收费用
        BigDecimal amount = calculationAmount(parkingRecordModel, chargeTypeModel);
        chargeRecordModel.setAmount(amount);
        //save计费记录
        chargeRecordRepository.save(chargeRecordModel);
        return chargeRecordModel;
    }

    /**
     * 计算停车费
     *
     * @param parkingRecordModel
     * @param chargeTypeModel
     * @return
     */
    private BigDecimal calculationAmount(ParkingRecordModel parkingRecordModel, ChargeTypeModel chargeTypeModel) {
        //包月或者免费直接返回0
        if (chargeTypeModel.getChargeCycle() == 3 || chargeTypeModel.getChargeCycle() == 2) {
            return new BigDecimal("0");
        }

        //停车总时长 分钟
        Long totalMinute = ((parkingRecordModel.getEndTime().getTime() - parkingRecordModel.getStartTime().getTime()) / 60000);
        //免费时间内
        if (null != chargeTypeModel.getFreeTime() && chargeTypeModel.getFreeTime() >= totalMinute.intValue()) {
            return new BigDecimal("0");
        }
        //总时间 小时。 不足一小时的情况需要考虑，大于免费时间后不足一小时都按照一小时算
        int totalHour = totalMinute.intValue() / 60;

        //计算方式 每天上限X每天最高收费+剩余的小时X小时收费

        //总天数
        int totalDay = totalHour / 24;
        //小时数量
        int leftHour = totalHour % 24;
        return new BigDecimal(totalDay).multiply(chargeTypeModel.getDayMaxAmount())
                .add(new BigDecimal(leftHour).multiply(chargeTypeModel.getUnitPrice()));

    }

    /**
     * 支付成功， 在出场时支付完成后调用
     * 1 查找支付单
     * 2 查找停车单，把停车记录设置未出场
     *
     * @param chargeId  对应的计费记录id
     * @param payType   支付类型
     * @param payAmount 实际支付金额，可能和应收不一致
     */
    @Override
    @Transactional
    public Response<ChargeRecordModel> paySuccess(Long chargeId, Short payType, BigDecimal payAmount) {
        //查询计费单
        ChargeRecordModel chargeRecordModel = chargeRecordRepository.getById(chargeId);
        if (null == chargeRecordModel) {
            throw new RuntimeException("无法找到计费单");
        }
        chargeRecordModel.setActualAmount(payAmount);
        chargeRecordModel.setPayType(payType);
        chargeRecordModel.setPayTime(new Date());
        chargeRecordModel.setStatus((short) 1);
        chargeRecordRepository.update(chargeRecordModel);
        //查询停车单 把停车单设置未已经出场
        ParkingRecordModel parkingRecordModel = parkingRecordRepository.getById(chargeRecordModel.getParkingRecordModel().getId());
        parkingRecordModel.setStatus((short) 2);
        parkingRecordRepository.update(parkingRecordModel);
        return Response.succ(chargeRecordModel);
    }

    /**
     * 查询指定日期所有的停车费用
     * 查询当天所有费用不属于高频操作 直接数据库sum 得到
     *
     * @param date
     * @return
     */
    @Override
    public Response<BigDecimal> queryTotalAmount(Date date) {
        return null;
    }

    /**
     * 筛选计费类型
     * 根据车类型 和车牌号进行选择。
     * 如果车牌有包月或者数据购买了车位的则根据协议价格收费或者免费
     * 没有办理包月的根据车类型选择计费类型
     *
     * @param cartNo
     * @param cartType
     * @return
     */
    private ChargeTypeModel filterChargeType(String cartNo, CartType cartType) {
        //1 获取所有的计费类型 可以使用cache策略，
        List<ChargeTypeModel> chargeTypeModelList = chargeTypeRepository.queryAll();
        if (CollectionUtils.isEmpty(chargeTypeModelList)) {
            throw new RuntimeException("未设置计费类型");
        }
        for (ChargeTypeModel chargeTypeModel : chargeTypeModelList) {
            //车型判断
            if (cartType != chargeTypeModel.getCartType()) {
                continue;
            }
            //包月或者其他自定义类型判断 xxxxxxx
            return chargeTypeModel;


        }
        throw new RuntimeException("未找到计费类型");
    }


}
