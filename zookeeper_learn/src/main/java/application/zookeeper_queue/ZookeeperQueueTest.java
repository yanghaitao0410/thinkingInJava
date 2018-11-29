package application.zookeeper_queue;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/11/29
 */
public class ZookeeperQueueTest {

    @Test
    public void zookeeperQueueTest() throws IOException {
        //模拟向队列放入任务
        new Thread(() -> {
            try {
                ZookeeperQueue<Work> queue = new ZookeeperQueue<>();
                int i = 0;
                while(true) {
                    Work work = new Work(i, "work_" + i);
                    System.out.println("当前放入任务" + i);
                    queue.add(work);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //模拟从队列拿任务
        new Thread(() -> {
            try {
                ZookeeperQueue<Work> queue = new ZookeeperQueue<>();
                while (true) {
                    if(!queue.isEmpty()) {
                        Work work = queue.poll();
                        System.out.println("开始处理任务" + work);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        System.in.read();
    }
}
