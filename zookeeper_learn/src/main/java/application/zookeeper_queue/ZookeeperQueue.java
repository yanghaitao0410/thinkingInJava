package application.zookeeper_queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * 分布式队列
 * 有任务发布线程和任务处理线程，
 * 发布线程负责将任务放到zookeeper队列中
 * 任务处理线程负责从队列中去除任务然后处理
 *
 * @author yht
 * @create 2018/11/28
 */
public class ZookeeperQueue<T> {
    public static final String TASK = "/TASK";

    CuratorFramework curatorFramework;

    public ZookeeperQueue() throws Exception {
        curatorFramework = CuratorClientUtil.getInstance();
//        curatorFramework.usingNamespace(TASK);
        initPathChildrenCache();
    }

    /**
     * 注册监听
     *
     * @return
     * @throws Exception
     */
    private void initPathChildrenCache() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, TASK, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("增加子节点" + pathChildrenCacheEvent.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点->" + pathChildrenCacheEvent.getData().getPath());
                    break;
                default:
                    break;
            }
        });
    }

    public void add(T t) {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(TASK + "/", SerizationUtil.toByteArr(t));
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public T poll() {
        String firstNodeName = null;
        try {
            List<String> childern = curatorFramework.getChildren().forPath(TASK);
            if (!childern.isEmpty()) {
                SortedSet<String> set = new TreeSet<>();
                set.addAll(childern);
                firstNodeName = set.first();
                byte[] bytes = curatorFramework.getData().forPath(TASK + "/" + firstNodeName);
                TimeUnit.SECONDS.sleep(1);
                curatorFramework.delete().forPath(TASK + "/" + firstNodeName);
                return (T) SerizationUtil.toObject(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmpty() {
        try {
            List<String> childern = curatorFramework.getChildren().forPath(TASK);
            TimeUnit.SECONDS.sleep(1);
            return childern.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
