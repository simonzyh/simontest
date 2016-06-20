package storm.local.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import storm.local.StormRedisUtils;

import java.util.Map;
import java.util.Random;

public class WordReader extends BaseRichSpout {
    Log log = LogFactory.getLog(this.getClass());
    private SpoutOutputCollector collector;
    private Random rand;

    public void ack(Object msgId) {
        log.warn("OK:" + msgId);
    }

    public void close() {
        log.warn("CLOSE ");
    }

    public void fail(Object msgId) {
        log.warn("FAIL:" + msgId);
    }

    public void open(Map conf, TopologyContext context,
                     SpoutOutputCollector collector) {
        this.collector = collector;
        this.rand = new Random();
    }


    public void nextTuple() {

        try {
            String toSay = null;
            while ((toSay = StormRedisUtils.lpop()) != null) {
                log.warn("WordReader-exec-start:" + toSay);
                this.collector.emit(new Values(toSay));
            }
            log.warn("WordReader-exec-end");
            Thread.sleep(1000 * 10);
        } catch (Exception e) {
            log.error(e);
        }

    }


    /**
     * Declare the output field "word"
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }
}
