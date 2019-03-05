package parkingmanage.model;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收费记录
 */
@Data
public class ChargeRecordModel extends BaseModel {

    /**
     * 计费对应的停车记录
     */
    private ParkingRecordModel parkingRecordModel;
    /**
     * 计费类型
     */
    private ChargeTypeModel chargeTypeModel;
    /**
     * 支付类型  1 现金 2 支付宝
     */
    private Short payType;
    /**
     * 支付实际
     */
    private Date payTime;

    /**
     * 需要支付金额
     */

    private BigDecimal amount;
    /**
     * 0 等待支付 1 成功 2 失败  3 免除
     */
    private Short status;
    /**
     * 实际支付金额
     */
    private BigDecimal actualAmount;
    /**
     * 收费员
     */
    private String chargeUser;
}
