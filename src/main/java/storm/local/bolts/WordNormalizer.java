package storm.local.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WordNormalizer extends BaseBasicBolt {
    Log log = LogFactory.getLog(this.getClass());

    public void cleanup() {

        log.warn("WordNormalizer -cleanup");
    }

    /**
     * The bolt will receive the line from the
     * words file and process it to Normalize this line
     * <p>
     * The normalize will be put the words in lower case
     * and split the line to get all words in this
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String sentence = input.getString(0);
            log.info("WordNormalizer-execute:" + sentence);
            String[] words = sentence.split("-");
            for (String word : words) {
                word = word.trim();
                if (!word.isEmpty()) {
                    word = word.toLowerCase();
                    collector.emit(new Values(word));
                }
            }
        } catch (Exception e) {
            log.error(e);
        }

    }


    /**
     * The bolt will only emit the field "word"
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
