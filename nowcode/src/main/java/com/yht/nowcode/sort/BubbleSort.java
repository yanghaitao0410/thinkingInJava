package com.yht.nowcode.sort;

import java.util.Arrays;

import static com.yht.nowcode.sort.CompareUtil.*;

public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        /**
         * 优化后的冒泡，从开始一直交换到最后，
         *  保证每一次在交换的范围将该区域的最大值冒泡到末端，然后区域缩小一位
         *  e:每一次的比较区域，一开始是整个数组
         *  j:比较的索引，从0开始
         */
       for(int e = arr.length - 1; e > 0; e--) {
           for(int j = 0; j < e; j++) {
               if(arr[j] > arr[j + 1]) {
                   swop(arr, j, j + 1);
               }
           }
       }
    }

    private static void swop(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void rigthMethod(int[] arr) {
        Arrays.sort(arr);
    }

    public int[] randArr() {
        int size = (int) (Math.random() * 100);
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) {
            arr[i] =  (int)(Math.random() * 1000) - (int)(Math.random() * 1001);
        }
        return arr;
    }

    public boolean compareMethod(int[] arr) {
        int[] arr1 = copyArr(arr);
        int[] arr2 = copyArr(arr);
        int testSize = 500000;
        boolean resultFlag = true;
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        testBulbble:
        for(int i = 0; i < testSize; i++) {
            rigthMethod(arr1);
            bubbleSort(arr2);
            for(int j = 0; j < arr.length; j++) {
                if(arr1[j] != arr2[j]) {
                    resultFlag = false;
                    break testBulbble;
                }
            }
        }
        return resultFlag;
    }

    public int[] copyArr(int[] arr) {
        int[] copyArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;
    }

    public static void main(String[] args) {
        //boolean compare = compareMethod(randArr());
        //System.out.println(compare ? "success!!" : "Fucking fucked");

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            bubbleSort(arr1);
            CompareUtil.rightMethod(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        bubbleSort(arr);
        printArray(arr);
    }
}
