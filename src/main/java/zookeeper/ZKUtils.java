package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**
 * ZooKeeper工具类
 *
 * @author Administrator
 */
public class ZKUtils {


    private static ZooKeeper zk = null;
    private static String rootPath = "/mmp";
    public final static String CONFIGPATH = rootPath + "/CONFIG";//每个业务系统在该节点下创建自己的临时节点  有配置项更新时就通知每一个业务系统的子节点

    private static Logger log = LoggerFactory.getLogger(ZKUtils.class);
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void start() {
        getInstance();
    }

    public static void connection() {
        String connections = "192.168.38.38:2181";//props.getProperty("zk.server");

        log.info("建立连接 " + connections);

        BaseWatcher watcher = new BaseWatcher();
        try {
            zk = new ZooKeeper(connections, 20, watcher);
        } catch (IOException e) {
            log.error("建立连接异常：", e);
        }

    }

    /**
     * 获取zk  当连接失败时获取等待
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
     * 监听ZK的连接状态
     * 成功是初始化 超时后重新连接
     *
     * @author Yehua.zhang
     */
    private static class BaseWatcher implements Watcher {
        public void process(WatchedEvent event) {
            log.info("zk出现变更事件" + event.getPath() + " " + event.getType());
            //不需要监听连不上的事件  zk会一直重新连接
            if (event.getState() == Event.KeeperState.Disconnected) {
                log.info("zk连接断开  ");
                latch.countDown();
                latch = new CountDownLatch(1);

            }
            //超时后重连
            else if (event.getState() == Event.KeeperState.Expired) {
                log.info("zk 连接超时 ");

                connection();
            }
            //已经连接上
            else if (event.getState() == Event.KeeperState.SyncConnected) {
                log.info("zk 连接成功 ");
                try {
                    Stat s = zk.exists(rootPath, false);
                    if (s == null) {
                        zk.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        zk.create(CONFIGPATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                    }

                    String path = zk.create(CONFIGPATH + "/CLIENT_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                    latch.countDown();

                    log.info("ZK初始化成功  状态是 " + zk.getState() + " " + path);


                    //存在接受配置项变更通知的节点
                    ZKUtils.getInstance().exists(path, new Watcher() {

                        public void process(WatchedEvent event) {
                            System.out.println("收到配置项节点变更通知 " + event.getType() + " " + event.getPath());
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
                    log.error("ZK环境初始化异常 ", e);
                }


            }
            zk.register(new BaseWatcher());
        }


    }
}
