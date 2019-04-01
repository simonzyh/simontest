package parkingmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import parkingmanage.constants.CartType;
import parkingmanage.constants.ParkingProperties;
import parkingmanage.model.ChargeRecordModel;
import parkingmanage.model.ParkingRecordModel;
import parkingmanage.repository.ParkingRecordRepository;
import parkingmanage.service.ChargeService;
import parkingmanage.service.ParkingService;
import parkingmanage.vo.Response;

import java.util.Date;


/**
 * 停车相关服务
 * 负责汽车进场，出场，车位剩余数量显示
 */
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    ParkingRecordRepository parkingRecordRepository;
    @Autowired
    ChargeService chargeService;

    /**
     * 汽车进场
     * 1 根据车牌 和车类型生产停车记录单。
     * 2 占用停车位，占用失败则提示车位不够.
     * 3 查询当前车辆进去后剩余车位。用于进场完成后显示剩余车位
     *
     * @param cardNo
     * @param cartType
     * @return 剩余车位数量
     */
    @Override
    @Transactional
    public Response<Integer> enter(String cardNo, String enterNo, CartType cartType) {
        //生产停车记录单
        ParkingRecordModel parkingRecordModel = new ParkingRecordModel();
        parkingRecordModel.setCardNo(cardNo);
        parkingRecordModel.setCartType(cartType);
        parkingRecordModel.setEnterNo(enterNo);
        parkingRecordModel.setStartTime(new Date());
        parkingRecordModel.setDataVersion(1);
        //占用车位
        //INCRBY 1 ，如果返回数据 >  总车位则提示车位满了
        int redisCount = 100;
        if (redisCount > ParkingProperties.TOTAL_PARKING_SPACE) {
            //这里由于已经占用了一次 需要回滚
            //redis INCRBY -1
            return Response.fail("100", "车位不够");
        }
        parkingRecordRepository.save(parkingRecordModel);
        return leftParkingSpace();

    }

    /**
     * 汽车出场
     * 1 查询出停车中的停车记录
     * 2 生产计费单
     * 4 返回计费数据给收费处，等待支付
     *
     * @param cardNo
     * @return
     */
    @Override
    @Transactional
    public Response<ChargeRecordModel> out(String cardNo, String exitNo) {
        //1 查询出停车中的停车记录
        ParkingRecordModel parkingRecordModel = parkingRecordRepository.getNotOutCartByNo(cardNo);

        if (null == parkingRecordModel) {
            return Response.fail("101", "未找到进场记录,请联系保安");
        }
        parkingRecordModel.setExitNo(exitNo);
        parkingRecordModel.setEndTime(new Date());
        parkingRecordRepository.update(parkingRecordModel);

        ChargeRecordModel chargeRecordModel = chargeService.submit(parkingRecordModel);

        return Response.succ(chargeRecordModel);
    }

    /**
     * 查询当前剩余车位
     * 车位使用情况使用redis 计数器实现
     * 当有车辆进入是 INCRBY 1
     * 车辆出去时     INCRBY -1
     * 查询时直接查询redis的数据返回
     *
     * @return
     */
    @Override
    public Response<Integer> leftParkingSpace() {
        //这里查询redis操作
        int redisCount = 100;

        return Response.succ(ParkingProperties.TOTAL_PARKING_SPACE - redisCount);
    }
}
