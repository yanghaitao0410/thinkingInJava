package code_java.synch;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable接口和Future接口测试
 *
 * 输入路径和关键字，统计出现关键字的文件数目
 */
public class FutureTest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.print("Enter base directory(e.g. /usr/local/jdk5.0/src): ");
            String directory = scanner.nextLine();
            System.out.print("Enter keyword(e.g. volatile): ");
            String keyword = scanner.nextLine();

            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<>(counter);
            Thread t = new Thread(task);
            t.start();
            System.out.println("thread: " + Thread.currentThread().getName());


            try {
                System.out.println(task.get() + " matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        int largestPoolSize = MatchCounter.threadCount.get();
        System.out.println("largest pool size = " + largestPoolSize);
    }
}
