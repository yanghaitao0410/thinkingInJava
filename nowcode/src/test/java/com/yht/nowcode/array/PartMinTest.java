package com.yht.nowcode.array;

import org.junit.Test;

public class PartMinTest {

    @Test
    public void test() {
        while (true) {
            int[] arr = new int[(int) ((10 + 1) * Math.random())];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) ((100 + 1) * Math.random()) - (int) (100 * Math.random());
                System.out.printf("%d ", arr[i]);
            }
            System.out.println();

            System.out.println(PartMin.getPartMinIndex(arr));
        }

    }
}
