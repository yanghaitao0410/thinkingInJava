package code_java.synch;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter base directory(e.g. /usr/local/jdk5.0/src): ");
            String directory = scanner.nextLine();
            System.out.print("Enter keyword(e.g. volatile): ");
            String keyword = scanner.nextLine();

            //返回一个带缓存的线程池，该池在必要的时候创建线程，在线程空闲60秒后终止线程
            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            System.out.println("thread: " + Thread.currentThread().getName());
            try {
                System.out.println(result.get() + " matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            pool.shutdown();

            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largest pool size = " + largestPoolSize);

        }
    }

    static CountDownLatch latch = new CountDownLatch(1);

    public void m1() {
        System.out.println("m1:" + Thread.currentThread().getName());
        latch.countDown();
    }

    @Test
    public void test() throws InterruptedException {
        ThreadPoolTest t = new ThreadPoolTest();
        //Thread构造方法可以设置线程启动后运行的方法，不设置特定方法会运行默认run方法
        new Thread(() -> t.m1(), "t1").start();
        latch.await();
    }
}
