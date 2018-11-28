package java_api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper 权限控制
 *
 * @author yht
 * @create 2018/11/27
 */
public class AuthControlDemo implements Watcher {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static Stat stat = new Stat();
    private static ZooKeeper zooKeeper;

    @BeforeEach
    public void startUp() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(CONNECTED_STRING, 5000, new AuthControlDemo());
        countDownLatch.await();
    }

    /**
     * 创建带权限节点
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void createAuthNodeTest() throws KeeperException, InterruptedException {

//         为节点设置多种权限方式
        ACL acl = new ACL(ZooDefs.Perms.READ, new Id("digest", "root:1234"));
        ACL acl1 = new ACL(ZooDefs.Perms.READ, new Id("ip", "192.168.1.1"));
        List<ACL> acls = new ArrayList<>();
        acls.add(acl);
        acls.add(acl1);
        zooKeeper.create("/authx", "123".getBytes(), acls, CreateMode.PERSISTENT);

        //zooKeeper.addAuthInfo("digest", "root:1234".getBytes());
        //zooKeeper.create("/auth", "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);


        /*
            权限模式：（create/delete/admin/read/write）
            schema : 授权对象
            ip  :  192.168.1.1
            Digest : (userName:password)
            world : anyone 开放式的权限控制模式，数据节点的访问权限对所有用户开放
            super : 超级用户，可以对zookeeper上所有节点进行操作
         */
    }

    /**
     * 另起一客户端，删除之前创建的带权限节点
     */
    @Test
    public void testDeleteAuthNode() throws KeeperException, InterruptedException, IOException {
        zooKeeper.addAuthInfo("digest", "root:1234".getBytes());
        zooKeeper.addAuthInfo("ip", "192.168.1.1".getBytes());
        byte[] bytes = zooKeeper.getData("/authx", true, new Stat());
        System.out.println(new String(bytes));
        TimeUnit.SECONDS.sleep(2);
    }



    @Override
    public void process(WatchedEvent watchedEvent) {
        //当前连接状态为连接上，进入if里面，解除阻塞
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {

            switch (watchedEvent.getType()) {
                case None:
                    if (null == watchedEvent.getPath()) {
                        countDownLatch.countDown();
                        System.out.println("建立连接：" + watchedEvent.getState() + "-->" + watchedEvent.getType());
                        break;
                    }
                default:
                    break;
            }
        }
    }
}
