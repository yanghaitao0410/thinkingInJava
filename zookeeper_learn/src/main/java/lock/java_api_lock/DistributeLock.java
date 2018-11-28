package lock.java_api_lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/11/28
 */
public class DistributeLock {

    private ZooKeeper zooKeeper;
    private static final String ROOT_LOCK = "/LOCKS";
    private byte[] data = {1, 2};
    private String LOCK_ID;
    private int sessionTimeout;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributeLock() throws IOException, InterruptedException, KeeperException {
        this.zooKeeper = ZookeeperClient.getInstance();
        this.sessionTimeout = ZookeeperClient.getSessionTimeout();
        TimeUnit.SECONDS.sleep(1);
        Stat stat = zooKeeper.exists(ROOT_LOCK, true);
        if (stat == null) {
            zooKeeper.create(ROOT_LOCK, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public boolean lock() {
        try {
            //返回节点路径
            LOCK_ID = zooKeeper.create(
                    ROOT_LOCK + "/", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "->成功创建了lock节点[" + LOCK_ID + "]，开始去竞争锁");

            //获取根节点下面所有锁节点名称：0000000000 不包含根路径
            List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCK, true);
            SortedSet sortedSet = new TreeSet();
            for (String child : childrenNodes) {
                System.out.println("chileName : " + child);
                sortedSet.add(ROOT_LOCK + "/" + child);
            }
            if (LOCK_ID.equals(sortedSet.first())) {
                System.out.println(Thread.currentThread().getName() + "成功获得锁，锁节点为：[" + LOCK_ID + "]");
                return true;
            }
            SortedSet<String> lessThan = sortedSet.headSet(LOCK_ID);
            if (!lessThan.isEmpty()) {
                zooKeeper.exists(lessThan.last(), new LockWatcher(countDownLatch));
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                //程序运行到这里意味着如果会话超时或者节点被删除（释放了）
                System.out.println(Thread.currentThread().getName() + "成功获得锁，锁节点为[" + LOCK_ID + "]");
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unLock() {
        System.out.println(Thread.currentThread().getName() + "->开始释放锁：[" + LOCK_ID + "]");
        try {
            zooKeeper.delete(LOCK_ID, -1);
            System.out.println("节点["+LOCK_ID+"]成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(10);
        for(int i = 0; i< 10; i++) {
            new Thread(() -> {
                DistributeLock distributeLock = null;
                try {
                    distributeLock = new DistributeLock();
                    count.countDown();
                    count.await();
                    distributeLock.lock();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } finally {
                    if(distributeLock != null) {
                        distributeLock.unLock();
                    }
                }
            }).start();
        }
    }
}
