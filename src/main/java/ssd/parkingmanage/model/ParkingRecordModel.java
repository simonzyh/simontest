package parkingmanage.model;


import lombok.Data;
import parkingmanage.constants.CartType;

import java.util.Date;

/**
 * 停车记录
 */
@Data
public class ParkingRecordModel extends BaseModel {
    //车牌号
    private String cardNo;
    //车类型
    private CartType cartType;
    //停车开始时间
    private Date startTime;
    //停车结束时间
    private Date endTime;
    //停车场进口 编号
    private String enterNo;

    //停车场出场出口编号
    private String exitNo;
    //状态 1 停车中 2 已经出场
    private Short status;

}
