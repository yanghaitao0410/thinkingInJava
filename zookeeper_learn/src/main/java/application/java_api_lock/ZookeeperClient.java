package application.java_api_lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yht
 * @create 2018/11/28
 */
public class ZookeeperClient {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static int SessionTimeout = 5000;

    public static ZooKeeper getInstance () throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTED_STRING, SessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return SessionTimeout;
    }
}
