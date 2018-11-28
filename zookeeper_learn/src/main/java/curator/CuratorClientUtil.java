package curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author yht
 * @create 2018/11/28
 */
public class CuratorClientUtil {
    private CuratorFramework curatorFramework;
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.35:2181";

    public static CuratorFramework getInstance() {
        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.newClient(CONNECTED_STRING, 5000, 5000,
                        new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
        System.out.println("连接成功...");
        return curatorFramework;
    }

}
