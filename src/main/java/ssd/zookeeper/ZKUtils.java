package ssd.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    public final static String CONFIGPATH = rootPath + "/CONFIG";

    private static Logger log = LoggerFactory.getLogger(ZKUtils.class);
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void start() {
        getInstance();
    }

    public static void connection() {
        String connections = "172.30.2.173:2181,172.30.2.173:2182,172.30.2.173:2183";//props.getProperty("zk.server");


        BaseWatcher watcher = new BaseWatcher();
        try {
            zk = new ZooKeeper(connections, 20, watcher);
        } catch (IOException e) {
            log.error(" ", e);
        }


    }

    /**
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
        connection();
        ZooKeeper zk= getInstance();
        Thread.sleep(100000);

    }

    /**

     *
     * @author Yehua.zhang
     */
    private static class BaseWatcher implements Watcher {
        public void process(WatchedEvent event) {
            log.info("Watcher" + event.getPath() + " " + event.getType());
             if (event.getState() == Event.KeeperState.Disconnected) {
                log.info("zk断开  ");
                latch.countDown();
                latch = new CountDownLatch(1);
                 connection();

            }
             else if (event.getState() == Event.KeeperState.Expired) {
                log.info("zk 过期 ");

                connection();
            }
             else if (event.getState() == Event.KeeperState.SyncConnected) {
                log.info("zk 连接上 ");
                try {
                    Stat s = zk.exists(rootPath, false);
                    if (s == null) {
                        zk.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        zk.create(CONFIGPATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                    }

                    String path = zk.create(CONFIGPATH + "/CLIENT_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                    latch.countDown();

                    log.info("ZK  " + zk.getState() + " " + path);


                     ZKUtils.getInstance().exists("/mmp", new Watcher() {

                        public void process(WatchedEvent event) {
                             try {
                                 log.info("Watcher /mmp" + event.getPath() + " " + event.getType());

                                System.out.println(new String(ZKUtils.getInstance().getData(event.getPath(), false,new Stat())));

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
            //zk.register(new BaseWatcher());
        }


    }
}
