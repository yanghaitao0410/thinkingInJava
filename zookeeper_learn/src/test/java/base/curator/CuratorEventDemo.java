package base.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * curator节点监听
 *
 * @author yht
 * @create 2018/11/28
 */
public class CuratorEventDemo {

    /**
     * 有三种watcher来做节点的监听
     * pathcache 监听一个路径下子节点的创建、删除、节点数据更新
     * nodecache 监听一个节点的创建、删除、更新
     * TreeCache pathcache+nodecache合体（监听路径下所有节点的创建、删除、更新）
     * 这三种watcher都会缓存路径下的所有子节点的数据
     */

    @Test
    public void nodeCacheTest() throws Exception {
        CuratorFramework curatorFramework = CuratorClientUtil.getInstance();
        //第三个参数为缓存数据是否做压缩处理
        NodeCache cache = new NodeCache(curatorFramework, "/base/curator", false);
        //true增加初始操作
        cache.start(true);

        cache.getListenable().addListener(() -> System.out.println("节点数据发生变化，变化后的结果："
                + new String(cache.getCurrentData().getData())));

        curatorFramework.setData().forPath("/base/curator", "菲菲".getBytes());
        System.in.read();
    }

    @Test
    public void pathChilderCacheTest() throws Exception {
        CuratorFramework curatorFramework = CuratorClientUtil.getInstance();
        //第三个参数为是否缓存数据
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, "/event", true);
        /*
        三种模式：NORMAL(初始化时为空)  BUILD_INITIAL_CACHE（start 返回之前会做reBuild操作）
            POST_INITIALIZED_EVENT 初始化后发送监听事件
         */
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("增加子节点");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                default:
                    break;
            }
        });

        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event", "event".getBytes());
        TimeUnit.SECONDS.sleep(1);
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/event/event1", "event".getBytes());
        TimeUnit.SECONDS.sleep(1);
        curatorFramework.setData().forPath("/event/event1", "222".getBytes());
        TimeUnit.SECONDS.sleep(1);
        curatorFramework.delete().forPath("/event/event1");
        System.in.read();
    }

}
