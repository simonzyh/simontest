package storm.local.bolts;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import storm.local.StormRedisUtils;

import java.util.Map;

public class WordCounter extends BaseBasicBolt {
    Log log = LogFactory.getLog(this.getClass());
    Integer id;
    String name;


    /**
     * At the end of the spout (when the cluster is shutdown
     * We will show the word counters
     */
    @Override
    public void cleanup() {
        log.warn("-- WordCounter cleanup [" + name + "-" + id + "] --");

    }

    /**
     * On create
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {

        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }


    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String str = input.getString(0);
            log.warn("WordCounter-execute:" + str);
            StormRedisUtils.increment(str);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
