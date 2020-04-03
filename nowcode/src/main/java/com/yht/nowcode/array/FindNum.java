package com.yht.nowcode.array;

/**
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * Consider the following matrix:
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * <p>
 * Given target = 5, return true.
 * Given target = 20, return false.
 *
 * @author yht
 * @create 2018/12/3
 */
public class FindNum {

    /**
     * 从右上角开始查找。矩阵中的一个数，它左边的数都比它小，下边的数都比它大。
     * 因此，从右上角开始查找，就可以根据 target 和当前元素的大小关系来缩小查找区间。
     * 从开始位置查找则不可以缩减区间
     * 复杂度：O(M + N) + O(1)
     *
     * 当前元素的查找区间为左下角的所有元素，例如元素 12 的查找区间如下：
     *
     * @param arr
     * @param num
     * @return
     */
    public static boolean findNum(int[][] arr, int num) {
        if (num < arr[0][0] || arr == null || arr.length == 0 || arr[0].length == 0) {
            return false;
        }
        int colCur = arr[0].length - 1; //列
        int rowCur = 0; //行
        while (rowCur < arr.length && colCur >= 0) {

            if (num == arr[rowCur][colCur]) {
                return true;
            } else if (num > arr[rowCur][colCur]) {
                rowCur++;
            } else {
                colCur--;
            }
        }
        return false;
    }




}
