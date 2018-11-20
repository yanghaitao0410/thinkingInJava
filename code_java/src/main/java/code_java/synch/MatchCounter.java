package code_java.synch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;
    public static AtomicInteger threadCount = new AtomicInteger(0);

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }
    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("thread: " + Thread.currentThread().getName());

        if(pool == null) {
            return normalThreadCall();
        } else {
            return threadPoolCall();
        }
    }

    /**
     * 通过线程池获取线程计算数目
     * @return
     */
    private Integer threadPoolCall() {
        int count = 0;

        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for(File file : files) {
                //若当前是目录，开启新线程遍历
                if(file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if(search(file)) {
                        count++;
                    }
                }
            }
            //统计子目录中符合的文件数目
            for (Future<Integer> result : results) {
                try {
                    //Future 中get方法调用被阻塞，直到计算完成
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 一个目录一个线程的方式获取数目
     * @return
     */
    private Integer normalThreadCall() {
        int count = 0;

        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for(File file : files) {
                //若当前是目录，开启新线程遍历
                if(file.isDirectory()) {
                    threadCount.incrementAndGet();
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask<>(counter);
                    results.add(task);
                    Thread thread = new Thread(task);
                    thread.start();
                } else {
                    if(search(file)) {
                        count++;
                    }
                }
            }
            //统计子目录中符合的文件数目
            for (Future<Integer> result : results) {
                try {
                    //Future 中get方法调用被阻塞，直到计算完成
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 文件中是否包含关键字
     * @param file
     * @return
     */
    private boolean search(File file) {
        try {
            try (Scanner scanner = new Scanner(file, "UTF-8")){
                boolean found = false;
                while (!found && scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(line.contains(keyword)) {
                        found = true;
                    }
                }
                return found;
            }
        } catch (IOException e) {
            return false;
        }
    }
}
