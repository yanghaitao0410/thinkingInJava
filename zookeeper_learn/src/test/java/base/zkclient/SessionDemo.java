package base.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

/**
 * @author yht
 * @create 2018/11/28
 */
public class SessionDemo {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";

    //建立连接
    @Test
    public void testConnection() {
        //传入连接ip和超时时间
        ZkClient zkClient = new ZkClient(CONNECTED_STRING, 5000);

        System.out.println(zkClient + "-> success");
    }

}
