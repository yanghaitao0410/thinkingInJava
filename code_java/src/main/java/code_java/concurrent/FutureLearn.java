package code_java.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author yht
 * @create 2020/2/29
 */
public class FutureLearn {

    @Test
    public void cancelTest() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Long> futureResult = es.submit(() -> {
            System.out.println("task started and running");
            Thread.sleep(1000);
            System.out.println("returning result");
            return ThreadLocalRandom.current().nextLong();
        });

        Thread.sleep(100);
        System.out.println("callable submitted");
        //after doing other stuff
        System.out.println("canceling task");
        boolean c = futureResult.cancel(false);
        System.out.println(c);
        //following will still throw java.util.concurrent.CancellationException
//        Long result = futureResult.get();
        es.shutdown();

    }

    @Test
    public void futureTaskTest () throws ExecutionException, InterruptedException {
        //futureTask第1种创建方式传入callable 有返回值
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                //simulating long running task
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished");
            return "The result";
        });

        new Thread(futureTask).start();
        System.out.println("Thread started");
        String s = futureTask.get();
        System.out.println(s);
    }

    @Test
    public void futureTaskTest2() throws ExecutionException, InterruptedException {
        //futureTask第2中创建方式，传入一个runnable和一个返回值
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                //simulating long running task
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished");

        }, "The result");

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(futureTask);
        String s = futureTask.get();
        System.out.println(s);
        es.shutdown();

    }

}
