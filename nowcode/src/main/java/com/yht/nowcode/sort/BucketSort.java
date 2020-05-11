package com.yht.nowcode.sort;

import static com.yht.nowcode.sort.CompareUtil.copyArray;
import static com.yht.nowcode.sort.CompareUtil.isEqual;
import static com.yht.nowcode.sort.CompareUtil.printArray;

/**
 * 桶排序
 * 本质不是排序，是分析出数据状态，将相同状态的数放入同一个桶中
 */
public class BucketSort {
    /**
     * 0~200范围的一堆无序数
     * @param arr
     */
    public static void bucketSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int[] bucket = new int[max + 1];
        for(int i = 0; i < arr.length; i++) {
            bucket[arr[i]] ++;
        }
        int index = 0;
        for(int i = 0; i < bucket.length; i++) {
            while(bucket[i]-- > 0) {
                arr[index++] = i;
            }
        }
    }

    public static void main(String[] args) {
        //boolean compare = compareMethod(randArr());
        //System.out.println(compare ? "success!!" : "Fucking fucked");

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 200;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            bucketSort(arr1);
            CompareUtil.rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        bucketSort(arr);
        printArray(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }
}
