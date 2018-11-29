package base.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * base.curator 节点操作
 * @author yht
 * @create 2018/11/28
 */
public class CuratorOperationDemo {

    CuratorFramework curatorFramework;

    @BeforeEach
    public void startUp() {
        curatorFramework = CuratorClientUtil.getInstance();
    }

    @Test
    public void testCreateNode() {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath("/base/curator/curator1/curator1_1", "1234".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteNode() {
        try {
            //默认版本号填写为-1
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/nodex");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNode() {
        Stat stat = new Stat();
        try {
            //获取值 storingStatIn（stat）将 节点的信息返回
            byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/base/curator/curator1/curator1_1");
            System.out.println(new String(bytes) + "--> stat" + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateNode() {
        try {
            Stat stat = curatorFramework.setData().forPath("/base/curator", "123".getBytes());
            System.out.println(stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步操作
     */
    @Test
    public void asynchronousTest() {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            //线程池
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                            System.out.println(Thread.currentThread().getName() + "->resultCode:" + event.getResultCode() + "->resultType:" + event.getType());
                            latch.countDown();
                        }
                    }, executorService).forPath("/mic", "123".getBytes());
            latch.await();
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务操作 curator独有
     */
    @Test
    public void transactionTest() throws Exception {

        //进行一项处理后通过and连接另一个处理 最后commit 若其中有操作出现错误 curator会将所有操作回滚
        Collection<CuratorTransactionResult> results =
                curatorFramework.inTransaction()
                        .create().forPath("/trans", "111".getBytes()).and()
                        .setData().forPath("/base/curator", "111".getBytes()).and()
                        .commit();

        for(CuratorTransactionResult result :results) {
            System.out.println(result.getForPath() + "->" + result.getType());
        }
    }
}
