package ssd.testkafka;

/**
 * User: simon
 * Date: 2015/8/18
 * Time: 16:26
 * description：
 */

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumerSample {
    private final ConsumerConnector consumer;

    private ConsumerSample() {
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "192.168.153.131:2181");

        //group 代表一个消费组
        props.put("group.id", "test-group");

        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        //序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }

    public static void main(String[] args) {
        new ConsumerSample().consume();
    }

    void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(ProducerSample.TOPIC, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        //  System.out.println("================"+consumerMap);
        KafkaStream<String, String> stream = consumerMap.get(ProducerSample.TOPIC).get(0);

        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext())
            System.out.println(it.next().message());
    }
}