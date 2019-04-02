package ssd.test;

import com.sun.jersey.client.apache.ApacheHttpClient;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class TestHttpCliect {

    public static void main(String[] args) throws Exception, IOException {

        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();


        int threadSize = 100;
        final CountDownLatch latch = new CountDownLatch(threadSize);
        final CountDownLatch latch1 = new CountDownLatch(10000);
        final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<Long>();
        Long l1 = System.currentTimeMillis();

        for (int i = 0; i < threadSize; i++) {
            new Thread() {
                public void run() {

                    ApacheHttpClient client = ApacheHttpClient.create();


                    for (int i = 0; i < 1000; i++) {
                        try {
                            // Thread.sleep(1L);
                            Long l1 = System.currentTimeMillis();
                            String url = "http://127.0.0.1/cebstest-webapp/codegen/student/1";
                            String content = client.resource(url).header("Connection", "close").get(String.class);
                            //System.out.println(content);
                            queue.add(System.currentTimeMillis() - l1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            latch1.countDown();
                        }
                    }

                    latch.countDown();
                }

            }.start();

        }
        while (latch.getCount() != 0) {
            Thread.sleep(12);
        }
        Long lall = 0L;
        for (Long l : queue) {
            lall += l;
        }

        System.out.println("总时间=" + (System.currentTimeMillis() - l1));

        System.out.println(" error=" + (10000 - latch1.getCount()));
        System.out.println("http 总次数 " + queue.size() + " 总时间 " + lall + " avg http time=" + lall / queue.size());


    }


}
