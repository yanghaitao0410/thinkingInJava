package application.master_selector.curator;

import base.curator.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 使用Curator实现master选举
 * @author yht
 * @create 2018/11/29
 */
public class MasterSelector {

    private static final String MASTER_PATH = "/CURSTOR_MASTER_PATH";

    public static void main(String[] args) throws IOException {
        CuratorFramework curatorFramework = CuratorClientUtil.getInstance();
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                //能够进入到这个方法的server表示为leader
                System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
            } //出方法会将leader释放掉
        });

        leaderSelector.autoRequeue(); //自动争抢
        leaderSelector.start(); //开始选举

        System.in.read();
    }
}
