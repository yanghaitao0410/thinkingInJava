package com.yht.nowcode.graph.apply;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class IsLandTest {
    int [][] arr;

    @Before
    public void before() {
        Random random = new Random();
        int flag;
        arr = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                flag = random.nextInt(101) - random.nextInt(100);
                arr[i][j] = flag > 0 ? 1 : 0;
                System.out.printf("%d ", arr[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    public void test() {
        System.out.println("islandCount:" + Island.getIslandCount(arr));
    }
}
