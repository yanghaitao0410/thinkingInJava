package code_java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yht
 * @create 2018/12/6
 */
public class ThreadDemo {
    static Thread thread1 = new Thread(() -> {
        System.out.println("thread1");
    });
    static Thread thread2 = new Thread(() -> {
        System.out.println("thread2");
    });
    static Thread thread3 = new Thread(() -> {
        System.out.println("thread3");
    });

    public static void main(String[] args) throws InterruptedException {
//        thread1.start();
//        thread1.join();
//        thread2.start();
//        thread2.join();
//        thread3.start();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.submit(thread3);
        executorService.shutdown();

    }
}
