package storm.local;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import storm.local.bolts.WordCounter;
import storm.local.bolts.WordNormalizer;
import storm.local.spouts.WordReader;


/**
 * Created by yehua.zyh on 2015/12/14.
 */
public class TopologyMainR {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        String name = args[0];
        int spoutSize = Integer.parseInt(args[1]);
        int normalizerSize = Integer.parseInt(args[2]);
        int counterSize = Integer.parseInt(args[3]);
        builder.setSpout("word-reader", new WordReader(), spoutSize);
        builder.setBolt("word-normalizer", new WordNormalizer(), normalizerSize).shuffleGrouping("word-reader");
        builder.setBolt("word-counter", new WordCounter(), counterSize).fieldsGrouping("word-normalizer", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(false);

        StormSubmitter.submitTopology(name, conf, builder.createTopology());

    }

}
