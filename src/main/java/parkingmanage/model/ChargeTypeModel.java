package parkingmanage.model;

import lombok.Data;
import parkingmanage.constants.CartType;

import java.math.BigDecimal;

/**
 * 计费类型
 * 计费类型由管理员创建，每一种车类型可以创建多种计费类型
 */
@Data
public class ChargeTypeModel extends BaseModel {

    private CartType cartType;
    /**
     * 计费名称
     */
    private String name;
    /**
     * 计费编号
     */
    private String code;
    /**
     * 计费周期  1 按小时收费， 2 包月 3 免费
     */
    private Short chargeCycle;

    /**
     * 免费时间 分钟
     */
    private Integer freeTime;
    /**
     * 单位时间停车费用
     */
    private BigDecimal unitPrice;
    /**
     * 一天最多收费 按时计费时有效
     */
    private BigDecimal dayMaxAmount;


}
