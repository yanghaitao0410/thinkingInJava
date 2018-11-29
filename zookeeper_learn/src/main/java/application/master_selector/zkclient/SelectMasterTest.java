package application.master_selector.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/11/28
 */
public class SelectMasterTest {

    private static final String CONNECTED_STRING =
            "192.168.70.129:2181,192.168.70.131:2181,192.168.70.133:2181,192.168.70.135:2181";

    @Test
    public void testSelectMaster() throws IOException, InterruptedException {
        List<MasterSelectot> selectots = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                ZkClient zkClient = new ZkClient(
                        CONNECTED_STRING, 5000, 5000, new SerializableSerializer());
                UserCenter userCenter = new UserCenter(i, "mc_" + i);
                MasterSelectot selectot = new MasterSelectot(zkClient, userCenter);
                selectots.add(selectot);
                TimeUnit.SECONDS.sleep(1);

            }
        } finally {
            for (MasterSelectot selectot : selectots) {
                selectot.stop();
            }
        }
    }

    //多线程进行master选举
    @Test
    public void testSelectMasterThread() throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                MasterSelectot selectot = null;
                try {
                    ZkClient zkClient = new ZkClient(
                            CONNECTED_STRING, 5000, 5000, new SerializableSerializer());
                    TimeUnit.SECONDS.sleep(1);
                    int id = new Random().nextInt(100);
                    UserCenter userCenter = new UserCenter(id, "mc_" + id);
                    selectot = new MasterSelectot(zkClient, userCenter);

                    TimeUnit.SECONDS.sleep(1);

                    selectot.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    selectot.stop();
                }
            }).start();
        }
        System.in.read();
    }

}
