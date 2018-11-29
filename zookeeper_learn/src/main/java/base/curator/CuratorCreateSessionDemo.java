package base.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.Test;

/**
 * @author yht
 * @create 2018/11/28
 */
public class CuratorCreateSessionDemo {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.35:2181";

    @Test
    public void testConnection() {
        //创建会话的两种方式 normal
        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.newClient(CONNECTED_STRING, 5000, 5000,
                        new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start(); //start() 方法启动

        //fluent 风格
        /*
            连接超时重试机制：
                ExponentialBackoffRetry 衰减重试 （间隔时间，重试次数）
                RetryNTimes 指定最大重试次数
                RetryOneTime 仅重试一次
                RetryUnitilElapsed 一直重试直到规定的时间
         */
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.builder()
                .connectString(CONNECTED_STRING)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("/base/curator")  //表示后续通过该连接创建的节点都是在这个根节点下
                .build();
        curatorFramework1.start();

        System.out.println("success");
    }
}
