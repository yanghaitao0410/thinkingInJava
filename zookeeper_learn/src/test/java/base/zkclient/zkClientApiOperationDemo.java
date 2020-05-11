package base.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/11/28
 */
public class zkClientApiOperationDemo {
    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";

    private static ZkClient getInstance() {
        return new ZkClient(CONNECTED_STRING, 5000);
    }

    ZkClient zkClient;

    @Before
    public void startUp() {
        zkClient = getInstance();
    }

    /**
     * 节点的增删改查
     */
    @Test
    public void testCreateNode() {
        zkClient = getInstance();

        //创建临时节点
//        zkClient.createPersistent("/zkClient");
        //zkClient 提供了递归创建父节点的功能
        zkClient.createPersistent("/zkClient/zkClient1", true);
        System.out.println("success");

    }

    @Test
    public void testDeleteNode() {

        //删除节点
//        zkClient.delete("/mic/mic1");
        //递归删除节点，传入根节点会将子节点一并删除
        zkClient.deleteRecursive("/mic");
    }

    @Test
    public void testGetNode() {
        //获取子节点
        List<String> children = zkClient.getChildren("/nodex");
        System.out.println(children);
    }

    @Test
    public void testWatch() throws InterruptedException {
        zkClient.subscribeDataChanges("/nodex", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点名称：" + dataPath +"-> 节点修改后的值：" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });

        zkClient.writeData("/nodex", "nodex");
        TimeUnit.SECONDS.sleep(1);

        /**
         * 可以对不存在的节点进行监听
         */
        zkClient.subscribeChildChanges("/node", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

            }
        });
    }
}
