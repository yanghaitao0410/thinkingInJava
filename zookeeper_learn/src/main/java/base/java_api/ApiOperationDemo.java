package base.java_api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author yht
 * @create 2018/11/27
 */
public class ApiOperationDemo implements Watcher {

    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";
    //    一种同步帮助，允许一个或多个线程等待在其他线程中执行的一组操作完成。
//    使用给定的计数初始化CountDownLatch。由于倒计时方法的调用，wait方法阻塞，直到当前计数达到零，
//    然后释放所有等待的线程，并立即返回任何后续的wait调用。这是一个一次性现象——计数无法重置。
//    如果需要重新设置计数的版本，可以考虑使用循环屏障
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static Stat stat = new Stat();
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(CONNECTED_STRING, 5000, new ApiOperationDemo());
        //阻塞
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
        //创建节点 返回节点名称  路径，数据，权限，类型
        //临时节点下面不能挂子节点
        String result = zooKeeper.create("/yht", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/yht", true, stat);
        System.out.println("创建成功：" + result);
        Thread.sleep(2000);

        //修改节点 版本号-1表示不进行版本控制
        zooKeeper.setData("/yht", "456".getBytes(), -1);
        Thread.sleep(2000);

        //删除节点
        //zooKeeper.delete("/nodes0000000001/node0000000001", -1);
        //Thread.sleep(2000);

//        //创建节点和子节点
//        zooKeeper.create("/nodex", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        TimeUnit.SECONDS.sleep(1);
//        //判断节点是否存在，并注册监听，返回stat实例
//        Stat stat1 = zooKeeper.exists("/nodex/node1", true);
//        if(stat1 == null) {
//            zooKeeper.create("/nodex/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            TimeUnit.SECONDS.sleep(1);
//        }
//
//        //修改子节点
//        zooKeeper.setData("/nodex/node1", "456".getBytes(), -1);
//        TimeUnit.SECONDS.sleep(1);

        //获取指定节点下的子节点
        List<String> childrenList = zooKeeper.getChildren("/nodex", true);
        System.out.println(childrenList);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //当前连接状态为连接上，进入if里面，解除阻塞
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {

            switch (watchedEvent.getType()) {
                case None :
                    if (null == watchedEvent.getPath()) {
                        countDownLatch.countDown();
                        System.out.println("建立连接：" + watchedEvent.getState() + "-->" + watchedEvent.getType());
                        break;
                    }
                case NodeCreated:
                    try {
                        System.out.println("创建节点成功，路径：" + watchedEvent.getPath() + "值："
                                + zooKeeper.getData(watchedEvent.getPath(), true, stat));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case NodeDeleted:
                    System.out.println("删除节点成功，路径：" + watchedEvent.getPath());
                    break;
                case NodeDataChanged:
                    try {
                        System.out.println("修改节点成功，路径：" + watchedEvent.getPath() + "值："
                                + zooKeeper.getData(watchedEvent.getPath(), true, stat));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case NodeChildrenChanged:
                    try {
                        System.out.println("创建子节点成功，路径：" + watchedEvent.getPath() + "值："
                                + zooKeeper.getData(watchedEvent.getPath(), true, stat));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
        System.out.println(watchedEvent.getType());
    }
}
