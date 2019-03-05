package parkingmanage.service;


import parkingmanage.constants.CartType;
import parkingmanage.model.ChargeRecordModel;
import parkingmanage.model.ChargeTypeModel;
import parkingmanage.model.ParkingRecordModel;
import parkingmanage.vo.Response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计费相关的 service
 */
public interface ChargeService {
    /**
     * 出场时
     * 提交计费根据停车记录生成计费信息 ，出口根据返回的计费
     * 数据显示收费信息给用户
     *
     * @param parkingRecordModel 当前计费记录对应的停车记录
     */
     ChargeRecordModel submit(ParkingRecordModel parkingRecordModel);

    /**
     * 支付成功 在出场时支付完成后调用
     *
     * @param chargeId  对应的计费记录id
     * @param payType   支付类型
     * @param payAmount 实际支付金额，可能和应收不一致
     */
    Response paySuccess(Long chargeId, Short payType, BigDecimal payAmount);

    /**
     * 查询指定日期所有的停车费用
     * @param date
     * @return
     */
    Response<BigDecimal> queryTotalAmount(Date date);

}
