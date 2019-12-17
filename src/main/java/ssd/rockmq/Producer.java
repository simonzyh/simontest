package ssd.rockmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class Producer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("buy-carts");
        producer.setNamesrvAddr("172.30.2.171:9876");
        try {
            producer.start();
            int i=0;
           while (i++<10) {
               Message msg = new Message("carts-del",
                       "tag"+i,
                       "key:"+i,
                       "Just for test.".getBytes());

               SendResult result = producer.send(msg);
               System.out.println("id:" + result.getMsgId() +
                       " result:" + result.getSendStatus());
           }


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            producer.shutdown();
        }
    }
}