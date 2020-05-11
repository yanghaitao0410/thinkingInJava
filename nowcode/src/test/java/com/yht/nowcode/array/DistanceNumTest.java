package com.yht.nowcode.array;

import org.junit.Test;

import static com.yht.nowcode.array.DistanceNum.getDistanceNum;

/**
 * @author yht
 * @create 2020/5/11
 */
public class DistanceNumTest {

    @Test
    public void test() {
        int[] arr = {5, 4, 3, 3, 3, 3};
        System.out.println(getDistanceNum(arr));
    }
}
