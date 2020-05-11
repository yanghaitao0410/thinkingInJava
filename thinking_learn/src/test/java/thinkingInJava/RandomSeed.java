package thinkingInJava;

import org.junit.Test;

import java.util.Random;

public class RandomSeed {

    @Test
    public void test1() {
        System.out.println("Random 不含构造方法");
        for(int i = 0; i < 5; i++) {
            Random random = new Random();
            for(int j = 0; j < 8; j++) {
                System.out.print(" " + random.nextInt(100) + ", ");
            }
            System.out.println();
        }
    }

    @Test
    public void test2() {
        System.out.println("Random 不含构造方法");
        for(int i = 0; i < 5; i++) {
            Random random = new Random(47);
            for(int j = 0; j < 8; j++) {
                System.out.print(" " + random.nextInt(100) + ", ");
            }
            System.out.println();
        }
    }
}
