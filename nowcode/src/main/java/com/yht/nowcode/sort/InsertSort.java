package com.yht.nowcode.sort;

import org.junit.Test;

import static com.yht.nowcode.sort.CompareUtil.*;

/**
 * 规定一开始数组的第一个数为有序区，然后当前指针指向第二个数，比较当前数和有序区最后数的大小，若当前数小，则交换
 * 然后继续比较当前数和有序区第二大的数...
 * 一直交换到有序区前一个数比当前数小或相等
 */
public class InsertSort {

    public void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i; j >= 0; j--) {
                if(arr[j] > arr[j + 1]) {
                    swop(arr, j, j + 1);
                }
            }
        }
    }

    @Test
    public void test1() {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            insertSort(arr1);
            CompareUtil.rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                CompareUtil.printArray(arr1);
                CompareUtil.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
