package application.master_selector.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/11/28
 */
public class MasterSelectot {
    private ZkClient zkClient;
    private UserCenter server;
    private UserCenter master;
    private boolean isRunning;
    private IZkDataListener listener;
    private final String MASTER_PATH = "/MASTER";
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public MasterSelectot(ZkClient zkClient, UserCenter server) {
//        System.out.println("[" + server + "] 去争抢master权限");
        isRunning = false;
        this.zkClient = zkClient;
        this.server = server;
        listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
//                System.out.println("master节点被删除->" + dataPath);
                //监听master节点删除事件，当前机器重新进入master选举
                selectMaster();
            }
        };

    }

    public void start() throws InterruptedException {
        if (!isRunning) {
            isRunning = true;
            //注册节点事件
            zkClient.subscribeDataChanges(MASTER_PATH, listener);
            while (true) { //每5秒进行一次master选举，监控
                selectMaster();
                TimeUnit.SECONDS.sleep(5);
            }

        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeDataChanges(MASTER_PATH, listener);
            releaseMaster();
        }
    }

    private void selectMaster() throws InterruptedException {
        if (!isRunning) {
            System.out.println("当前服务没有启动");
            return;
        }

        try {
            zkClient.createEphemeral(MASTER_PATH, server);
            //TimeUnit.SECONDS.sleep(1);
            System.out.println(server + "->节点竞选为master");
            master = server;

            //3秒后调用一次
            scheduledExecutorService.schedule(() -> {
                releaseMaster();
            }, 3, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
            UserCenter userCenter = zkClient.readData(MASTER_PATH, true);
            //TimeUnit.SECONDS.sleep(1);
            if (userCenter != null) {
                master = userCenter;
                System.out.println("当前master为：mc_id->" + master.getMc_id() + "->当前server为：mc_id->" + server.getMc_id());
            } else {
                selectMaster();
            }
        }
    }

    private void releaseMaster() {
        //释放锁（模拟master故障）
        //判断当前是否时master，只有master才需要释放
        if (isRunning && isMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    //判断当前server是否是master
    private boolean isMaster() {
        try {
            UserCenter center = zkClient.readData(MASTER_PATH, true);
            TimeUnit.SECONDS.sleep(1);
            if (center != null && center.equals(server)) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


}
