package parkingmanage.repository;

import parkingmanage.model.ChargeRecordModel;

/**
 * 计费记录储存操作
 */
public class ChargeRecordRepository {

    /**
     * 汽车出场时生成计费记录
     *
     * @param chargeRecordModel
     */
    public void save(ChargeRecordModel chargeRecordModel) {


    }

    /**
     * 出场时 缴费完成更新缴费结果
     *
     * @param chargeRecordModel
     */
    public void update(ChargeRecordModel chargeRecordModel) {


    }

    /**
     * 根据停车记录id查询计费单
     *
     * @param parkingRecordId
     * @return
     */
    public ChargeRecordModel getByParkingRecordId(Long parkingRecordId) {
        return null;
    }

    /**
     * 根据计费id查询计费记录
     *
     * @param id
     * @return
     */
    public ChargeRecordModel getById(Long id) {
        return null;
    }
}
