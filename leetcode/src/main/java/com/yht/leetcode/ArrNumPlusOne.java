package com.yht.leetcode;

import java.util.Arrays;

/**
 *
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1:
 *
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 * 示例 2:
 *
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/plus-one
 * @author yht
 * @create 2020/1/1
 */
public class ArrNumPlusOne {

    /*
        new一个变量存储是否需要进位
        while循环数组 如果+1之前是9 那么需要进位
    */
    public static int[] plusOne(int[] digits) {
        int arrSize = digits.length;
        if(arrSize == 1 && digits[0] == 0) {
            digits[0] = 1;
            return digits;
        }
        for(int i = arrSize - 1; i >= 0; i--) {
            int indexNum = digits[i];
            if(indexNum < 9) {
                digits[i] = indexNum + 1;
                break;
            }else if(digits[i] == 9) {
                digits[i] = 0;
                if(i == 0) {
                    int[] newArr = new int[arrSize + 1];
                    System.arraycopy(digits, 0, newArr, 1, arrSize);
                    newArr[0] = 1;
                    return newArr;
                }

            }

        }
        return digits;
    }

    public static void main(String[] args) {
        int [] arr = {9,9,9};
        Arrays.stream(plusOne(arr)).forEach(System.out::println);
    }



}
