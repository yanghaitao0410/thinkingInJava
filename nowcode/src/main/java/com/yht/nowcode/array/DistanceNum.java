package com.yht.nowcode.array;

import org.junit.Test;

/**
 * 数组中重复数字
 * 在一个长度为n的数组里的所有数字都在0~n-1的范围内。数组中某些数字是重复的，但不知道有几个数组是重复的，
 * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字
 * Input:{2, 3, 1, 0, 2, 5}
 * OutPut:2
 *
 * @author yht
 * @create 2018/12/3
 */
public class DistanceNum {

    /**
     * 没有重复数字返回-1
     *
     * 思路：数组元素在[0, n-1]范围内的问题，可以将值为i的元素调整在第i个位置上，调整过程中发现要调整的数字已经有了就直接返回
     *
     * @param arr
     * @return
     */
    public static int getDistanceNum(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            while (i != arr[i]) {
                //将当前位置的数字交换到和他值相等的位置,说明有重复数字
                if(arr[i] == arr[arr[i]]) {
                    return arr[i];
                }
                swap(arr, i, arr[i]);
            }
        }
        return -1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    @Test
    public void test() {
        int[] arr = {5, 4, 3, 3, 3, 3};
        System.out.println(getDistanceNum(arr));
    }
}
