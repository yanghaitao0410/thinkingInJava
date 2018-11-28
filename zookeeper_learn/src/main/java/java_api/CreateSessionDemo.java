package java_api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 建立zookeeper连接
 * @author yht
 * @create 2018/11/27
 */
public class CreateSessionDemo {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.35:2181";
//    一种同步帮助，允许一个或多个线程等待在其他线程中执行的一组操作完成。
//    使用给定的计数初始化CountDownLatch。由于倒计时方法的调用，wait方法阻塞，直到当前计数达到零，
//    然后释放所有等待的线程，并立即返回任何后续的wait调用。这是一个一次性现象——计数无法重置。
//    如果需要重新设置计数的版本，可以考虑使用循环屏障
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {

        ZooKeeper zooKeeper = new ZooKeeper(CONNECTED_STRING, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //当前连接状态为连接上，进入if里面，解除阻塞
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                    System.out.println(watchedEvent.getState());
                }
            }
        });
        //阻塞
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
    }
}
