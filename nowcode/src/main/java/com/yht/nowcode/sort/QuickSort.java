package com.yht.nowcode.sort;

import static com.yht.nowcode.sort.CompareUtil.*;

public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int L, int R) {
        if(L < R) {
            //从数组中随机选择一个数放到该数组的最后作为规定值，以达到期望时间复杂度
            swop(arr, L + (int) (Math.random() * (R - L + 1)), R);
            int[] p = partition(arr, L, R);
            quickSort(arr, L, p[0] - 1); //快速排序 等于区 左边
            quickSort(arr, p[1] + 1, R); //快速排序 等于区 右边
        }
    }

    public static int[] partition(int[] arr, int L, int R) {
        int less = L - 1; //小于区一开始在L左侧
        int more = R;  //大于区一开始包含

        while(L < more) { //当前指针没有指向大于区就一直循环
            if(arr[L] < arr[R]) { //左侧指针指向数值比规定值小
                //当前数和小于区后面的数交换，小于区向后扩充一位 ,指针向后移动一位
                swop(arr, ++less, L++);
            }else if(arr[L] > arr[R]) { //左侧指针指向数值比规定值大
                //当前数和大于区前面的数交换，大于区向前扩充一位，指针位置不变
                swop(arr, --more, L);
            }else { //指针指向数值和规定值相等，指针向下移动一位
                L++;
            }
        }
        //将规定值和大于区第一个数交换，使得规定值到等于区
        swop(arr, more, R);

        //返回等于区的左边界和右边界
        return new int[] { less + 1, more };
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort(arr1);
            rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr3);
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
