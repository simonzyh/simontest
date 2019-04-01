package ssd.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class TestRedis {

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("192.168.153.131", 6380);

        Long l1 = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 1; i < 30000; i++) {
            jedis.lpush("text:list", "hello-storm-world");


        }

        System.out.println(System.currentTimeMillis() - l1);
    }

}
