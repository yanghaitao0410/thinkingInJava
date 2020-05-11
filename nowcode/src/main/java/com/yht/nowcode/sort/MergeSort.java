package com.yht.nowcode.sort;

import static com.yht.nowcode.sort.CompareUtil.copyArray;
import static com.yht.nowcode.sort.CompareUtil.generateRandomArray;
import static com.yht.nowcode.sort.CompareUtil.isEqual;

/**
 * 归并排序
 * 先递归归并排序，排好左边和右边，然后外排总体
 * 若归并排序的数组只有一个数，直接返回
 */
public class MergeSort {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int L, int R) {
        if(L == R) {
            return;
        }

        int mid = L + ((R-L) >> 1);
        mergeSort(arr, L, mid);
        mergeSort(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int leftIndex, int mid, int rightIndex) {
        int[] help = new int[rightIndex - leftIndex + 1];
        int p1 = leftIndex;
        int p2 = mid + 1;
        int i = 0;
        while(p1 <= mid && p2 <= rightIndex) {
            help[i++] = arr[p1] >= arr[p2] ? arr[p2++] : arr[p1++];
        }
        while(p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while(p2 <= rightIndex) {
            help[i++] = arr[p2++];
        }
        for(int index = 0; index < help.length; index++) {
            arr[leftIndex + index] = help[index];
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
            mergeSort(arr1);
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
