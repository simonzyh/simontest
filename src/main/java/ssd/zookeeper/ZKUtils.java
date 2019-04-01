package ssd.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**
 * ZooKeeper??????
 *
 * @author Administrator
 */
public class ZKUtils {


    private static ZooKeeper zk = null;
    private static String rootPath = "/mmp";
    public final static String CONFIGPATH = rootPath + "/CONFIG";//?????????????????????????????  ???????????????????????????????

    private static Logger log = LoggerFactory.getLogger(ZKUtils.class);
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void start() {
        getInstance();
    }

    public static void connection() {
        String connections = "192.168.38.38:2181";//props.getProperty("zk.server");

        log.info("???????? " + connections);

        BaseWatcher watcher = new BaseWatcher();
        try {
            zk = new ZooKeeper(connections, 20, watcher);
        } catch (IOException e) {
            log.error("????????????", e);
        }

    }

    /**
     * ???zk  ????????????????
     *
     * @return
     */
    public static ZooKeeper getInstance() {
        try {

            latch.await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        return zk;

    }

    public static void main(String[] args) throws Exception {

        Map<String, Thread> map = new HashMap<String, Thread>();
        Thread t = new Thread() {
            public void run() {
                System.out.println(123);
            }
        };
        t.start();
        map.put("123", t);
        Thread.sleep(1000 * 5);
        Thread t1 = map.get("123");
        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(t1.isInterrupted());
        Thread.sleep(1000 * 5);
    }

    /**
     * ????ZK????????
     * ????????? ?????????????
     *
     * @author Yehua.zhang
     */
    private static class BaseWatcher implements Watcher {
        public void process(WatchedEvent event) {
            log.info("zk?????????" + event.getPath() + " " + event.getType());
            //???????????????????  zk????????????
            if (event.getState() == Event.KeeperState.Disconnected) {
                log.info("zk??????  ");
                latch.countDown();
                latch = new CountDownLatch(1);

            }
            //?????????
            else if (event.getState() == Event.KeeperState.Expired) {
                log.info("zk ?????? ");

                connection();
            }
            //?????????
            else if (event.getState() == Event.KeeperState.SyncConnected) {
                log.info("zk ?????? ");
                try {
                    Stat s = zk.exists(rootPath, false);
                    if (s == null) {
                        zk.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        zk.create(CONFIGPATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                    }

                    String path = zk.create(CONFIGPATH + "/CLIENT_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                    latch.countDown();

                    log.info("ZK????????  ???? " + zk.getState() + " " + path);


                    //?????????????????????
                    ZKUtils.getInstance().exists(path, new Watcher() {

                        public void process(WatchedEvent event) {
                            System.out.println("??????????????? " + event.getType() + " " + event.getPath());
                            try {

                                System.out.println(new String(ZKUtils.getInstance().getData(event.getPath(), this, new Stat())));

                            } catch (KeeperException e) {

                                e.printStackTrace();
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }
                    });


                } catch (Exception e) {
                    log.error("ZK??????????? ", e);
                }


            }
            zk.register(new BaseWatcher());
        }


    }
}
