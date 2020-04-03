package com.yht.nowcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得它们的和正好是S。如果有多对数字的和等于S，输出两个数的乘积最小的
     *
     * 思路：
     *  使用两个指针，一个指针指向末尾，一个指向开头。向中间遍历
     *  如果两个指针指向的和等于S，返回
     *  如果比S大，末尾的指针向前移动
     *  如果比S小，开头的的指针向后移动
     *
     * @param array
     * @param sum
     * @return
     */
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        if(array == null || array.length == 0) {
            return null;
        }
        int start = 0, end = array.length - 1;
        while (start < end) {
            int curSum = array[start] + array[end];
            if(curSum == sum) {
                return new ArrayList<>(Arrays.asList(array[start], array[end]));
            }
            if(curSum > sum) {
                end--;
            }
            if(curSum < sum) {
                start++;
            }
        }
        return null;
    }

    /**
     *
     * 输出所有和为S的连续正数序列
     * 例如输入和为100 的连续序列：
     *  [9, 10, 11, 12, 13, 14, 15, 16]
     *  [18, 19, 20, 21, 22]
     *
     *  思路：一开始序列从start=1， end=2开始
     *  一直遍历到end = sum
     *  当和大于sum，start向后移动一个位置
     *  当和小于sum，end向后移动一个位置
     *  等于将start到end之间的数都放入list中然后将start和end的位置都向后移动一个位置
     * @param sum
     * @return
     */
    public static ArrayList<ArrayList<Integer>> findContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int start = 1, end = 2;
        int curSum = start + end;
        while (end < sum) {
            if(curSum == sum) {
                ArrayList<Integer> tmp = new ArrayList();
                for(int i = start; i <= end; i++) {
                    tmp.add(i);
                }
                result.add(tmp);
                //start要向后移动一个位置，需要先将当前位置从总和中减去
                curSum -= start;
                start++;

                end++;
                curSum += end;
            } else if(curSum < sum) {
                end++;
                curSum += end;
            } else if(curSum > sum) {
                curSum -= start;
                start++;
            }
        }

        return result;
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     *   例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
     *   由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < array.length; i++) {
            Integer count = map.get(array[i]);
            if(count == null) {
                map.put(array[i], 1);
            } else {
                if(++count > array.length/2){
                    return array[i];
                }
                map.put(array[i], count);
            }
        }
        return 0;
    }

}











