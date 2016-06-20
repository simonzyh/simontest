/**
 * @Title: TestThreadPoll.java
 * @Package: test
 * @Description: TODO
 * @author 张业华
 * @date 2014-6-27 上午10:58:58
 */

package test;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张业华
 * @Description
 * @date 2014-6-27 上午10:58:58
 */

public class TestThreadPoll {
    public final static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.DiscardPolicy());
    final static LinkedList<String> list = new LinkedList<String>();

    public static void main(String[] args) throws Exception {


        new Thread() {
            public void run() {
                while (true) {
                    System.out.println(pool.getPoolSize() + " " + pool.getActiveCount() + " " + pool.getTaskCount());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }

        }.start();
        ThreadT t = new ThreadT();
        while (true) {
            if (pool.getPoolSize() < pool.getCorePoolSize()) {
                pool.execute(t);


            }


        }

    }

    public void changePoolSize(int size) {
        pool.setCorePoolSize(size);
    }

    static class ThreadT implements Runnable {

        ThreadT() {
        }

        /**
         * Description
         *
         * @see Runnable#run()
         */

         public void run() {

            System.out.println("线程运行" + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }
}


