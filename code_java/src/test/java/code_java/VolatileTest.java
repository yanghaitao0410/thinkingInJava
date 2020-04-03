package code_java;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yht
 * @create 2018/12/6
 */
public class VolatileTest {

    volatile int i = 0;

    public void add() {
        i++;
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    volatileTest.add();
                    TimeUnit.SECONDS.sleep(1);
                    volatileTest.add();
                    System.out.println(Thread.currentThread().getName() + "->" + volatileTest.i);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println(volatileTest.i);
    }



}
