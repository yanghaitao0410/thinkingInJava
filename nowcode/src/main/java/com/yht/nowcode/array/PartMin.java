package com.yht.nowcode.array;

/**
 * 在数组中找到一个局部最小的位置
 * 【题目】
 * 定义局部最小的概念：
 *      arr长度为1的时候，arr[0]是局部最小。
 *      arr长度为N（N>1）时，如果arr[0] < arr[1], 那么arr[0]是局部最小；
 *      如果arr[N - 1] < arr[N - 2],那么arr[N - 1]是局部最小；
 *      如果 0 < i < N - 1 ,并且arr[i - 1] > arr[i] 、arr[i + 1] > arr[i]
 *      那么arr[i]是局部最小。
 *
 *  给定无序数组arr, 已知arr中任意两个相邻的数都不相等，
 *  实现一个函数，只需返回arr中任意一个局部最小出现的位置即可。
 */
public class PartMin {

    /**
     * 实现思路：使用二分法解决
     *  首先判断数组的0位置和length - 1 位置是否有局部最小，有的话直接返回
     *  若没有局部最小，表示开头数字变化趋势为由大到小，结尾的数字变化趋势为由小到大，
     *  则在数组的中间位置必有局部最小，定位到数组的中间位置，若该位置为局部最小，直接返回，
     *  否则选取和开头或结尾数字变化相反的区域，继续二分查找，直到找到为止。
     *
     * @param arr
     * @return
     */
    public static int getPartMinIndex(int[] arr) {

        if(arr == null || arr.length == 0) {
            return -1;
        }

        if(arr.length == 1) {
            return 0;
        }

        if(arr [0] < arr[1]) { //开始位置局部最小
            return 0;
        }
        if(arr[arr.length - 1] < arr[arr.length - 2]) { //结束位置局部最小
            return arr.length - 1;
        }

        return getPartMinIndex(arr, 0, arr.length - 1);
    }

    /**
     * 在数组的有限区域寻找局部最小
     * @param arr
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public static int getPartMinIndex(int[] arr, int start, int end) {
        if(end >= arr.length || start < 0 || end < start) {
            return -1;
        }
        int mid = (start + end) / 2;
        if(mid - 1 < start || mid + 1 > end) {
            return -1;
        }
        if(arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
            return mid;
        }
        //运行到这里表示数组两边没有局部最小，边上是上升趋势
        //若中间往结尾的趋势是下将的，后半段必有局部最小，否则前半段必有局部最小
        if(arr[mid] > arr[mid + 1]) {
           return getPartMinIndex(arr, mid, end);
        } else {
           return getPartMinIndex(arr, start, mid);
        }
    }
}
