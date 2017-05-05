
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.taobao.metaq.client.MetaProducer;
import com.tmall.bbq.dto.AccChangeEventDTO;

import java.util.Date;

/**
 * Created by yehua.zyh on 2016/12/29.
 */
public class Test {
    private static MetaProducer metaProducer = new MetaProducer("bbq_dump");

    public static void main(String[] args) throws  Exception {
        metaProducer.start();
        AccChangeEventDTO accChangeEventDTO = new AccChangeEventDTO();

        accChangeEventDTO.setAccVersion(1);
        accChangeEventDTO.setRuleVersion(1234);
        accChangeEventDTO.setRuleType(4);
        accChangeEventDTO.setSellerId(1234567L);
        accChangeEventDTO.setBeginDate(new Date());
        accChangeEventDTO.setEndDate(new Date());
        accChangeEventDTO.setLimitNum(19);
        accChangeEventDTO.setLimitStatus(0);
        accChangeEventDTO.setBbqId("02-3267004-2100533963032");

        for(int i=0;i<100000;i++){
            accChangeEventDTO.setBuyerId((long) i);
            String metaqId = "accChange" + "_" + accChangeEventDTO.getRuleId() + "_" + accChangeEventDTO.getBbqId() + "_" + accChangeEventDTO.getAccVersion();

            Message msg = new Message("BBQ_DUMP_TOPIC", // topic
                    "accChange", // tag
                    metaqId, // key，消息的Key字段是为了唯一标识消息的，方便运维排查问题。如果不设置Key，则无法定位消息丢失原因。
                    JSON.toJSONString(accChangeEventDTO).getBytes());// body


            metaProducer.send(msg);
        }
    }
}
