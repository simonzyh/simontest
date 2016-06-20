package storm.local;

import redis.clients.jedis.Jedis;

/**
 * Created by yehua.zyh on 2016/1/12.
 */
public class StormRedisUtils {


    public static String lpop() {
        Jedis jedis = new Jedis("127.0.0.1", 6380);
        return jedis.lpop("text:list");
    }

    public static void increment(String key) {
        Jedis jedis = new Jedis("127.0.0.1", 6380);
        jedis.hincrBy("text:count", key, 1);

    }
}
