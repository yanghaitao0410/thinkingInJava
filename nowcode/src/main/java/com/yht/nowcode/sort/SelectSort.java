package com.yht.nowcode.sort;

import static com.yht.nowcode.sort.CompareUtil.copyArray;
import static com.yht.nowcode.sort.CompareUtil.generateRandomArray;
import static com.yht.nowcode.sort.CompareUtil.isEqual;

public class SelectSort {

    /**
     * 从当前范围选择最小值放到区域的第一个位置，然后区域向后移动一位，继续选择
     * @param arr
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for(int minIndex = 0; minIndex < arr.length - 1; minIndex++) {
            for(int j = minIndex + 1; j < arr.length; j++) {
                if(arr[minIndex] > arr[j]) {
                    CompareUtil.swop(arr, minIndex, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            selectSort(arr1);
            CompareUtil.rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
