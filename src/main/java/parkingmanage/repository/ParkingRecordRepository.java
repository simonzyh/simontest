package parkingmanage.repository;

import parkingmanage.model.ParkingRecordModel;

/**
 * 停车记录储存操作
 */
public class ParkingRecordRepository {

    /**
     * 汽车进场时
     * 保持一条新的停车记录
     *
     * @param parkingRecordModel
     */
    public void save(ParkingRecordModel parkingRecordModel) {

    }

    /**
     * 汽车出场时更新停车记录
     *
     * @param parkingRecordModel
     */
    public void update(ParkingRecordModel parkingRecordModel) {

    }

    /**
     * 根据汽车车牌查询汽车当前已经进场还未出场的车辆 status=1
     *
     * @param cartNo
     * @return
     */
    public ParkingRecordModel getNotOutCartByNo(String cartNo) {
        return null;
    }

    /**
     * 根据id查询停车记录
     *
     * @param id
     * @return
     */
    public ParkingRecordModel getById(Long id) {
        return null;
    }

}
