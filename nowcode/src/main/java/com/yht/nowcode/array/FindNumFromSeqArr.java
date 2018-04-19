package com.yht.nowcode.array;

/**
 * 在行列都排好的序的矩阵中找数
 * 【题目】
 * 在给定一个有N*M的整形矩阵matrix和一个整数K，matrix的每一行和每一列都是排好序的。
 * 实现一个函数，判断K是否在matrix中
 * 例如：
 * 0   1   2   5
 * 2   3   4   7
 * 4   4   4   8
 * 5   7   7   9
 * 如果K为7，返回true，如果K为6，返回false
 * 【要求】时间复杂度O(N+M),空间复杂度O(1)
 * <p>
 * 思路：(从右上或左下角开始都可以)
     * 指针一开始指向第一排最右的一个数：
     * 若当前数比比较值小，则指针向下移动
     * 若当前数比比较值大，则指针向右移动
     * 相等返回true
     * 若越界，返回false
 */
public class FindNumFromSeqArr {

    public static boolean findNumFromSeqArr(int[][] matrix, int num) {
        int m = matrix.length - 1, n = matrix[0].length - 1;
        while(m >= 0 && n >= 0) {
            if(matrix[m][n] > num) {
                m--;
            }else if(matrix[m][n] < num) {
                n--;
            }else {
                return true;
            }
        }
        return false;
    }


}











