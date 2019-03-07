package parkingmanage.service;


import parkingmanage.constants.CartType;
import parkingmanage.model.ChargeRecordModel;
import parkingmanage.vo.Response;

/**
 * 停车 相关的服务 facade
 */
public interface ParkingService {
    /**
     * 汽车进场
     *
     * @param cardNo
     * @param cartType
     */
    Response<Integer> enter(String cardNo, String enterNo, CartType cartType);

    /**
     * 汽车出场
     *
     * @param cardNo
     * @return
     */
    Response<ChargeRecordModel> out(String cardNo, String exitNo);

    /**
     * 查询当前剩余车位
     *
     * @return
     */
    Response<Integer> leftParkingSpace();
}
