package ssd.testkafka;

/**
 * User: simon
 * Date: 2015/8/18
 * Time: 16:15
 * description：
 */

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProducerSample {

    public final static String TOPIC = "mykafka";
    private final Producer<String, String> producer;

    private ProducerSample() {
        Properties props = new Properties();
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", "192.168.153.131:9092,192.168.153.131:9093,192.168.153.131:9094");

        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");

        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
        props.put("request.required.acks", "-1");

        producer = new Producer<String, String>(new ProducerConfig(props));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new Thread() {
                public void run() {
                    new ProducerSample().produce();
                }
            }.start();

        }
    }

    void produce() {

        String topic = TOPIC;
        List<KeyedMessage<String, String>> messageList = new ArrayList<KeyedMessage<String, String>>();
        for (int i = 10; i < 500000; i++) {
            String key = "-" + i;
            String data = "hello kafka message hello kafka message hello kafka message hello kafka message " + i;
            Long l1 = System.currentTimeMillis();
            messageList.add(new KeyedMessage<String, String>(topic, key, data));
            if (i % 100 == 0) {
                System.out.println(i);
                producer.send(messageList);
                messageList.clear();
            }


        }

    }
}