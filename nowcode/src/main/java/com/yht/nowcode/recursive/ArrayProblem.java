package com.yht.nowcode.recursive;

import org.junit.Test;

public class ArrayProblem {

    /**
     * 给你一个数组arr 和一个整数aim。如果可以任意选择arr中的数字，能不能累加得到aim?
     * 返回true或false（简化问题：arr和aim中都为正数）
     */
    public static boolean existArrSumAim(int[] arr, int aim) {
        return existArrSumAim(arr, aim, 0,0);
    }

    /**
     * 从arr的 i位置到数组最后位置 是否存在累加得到aim的数字
     *  每个位置有两种选择，要或是不要
     * @param arr
     * @param aim
     * @param sum
     * @param i
     * @return
     */
    public static boolean existArrSumAim(int[] arr, int i, int sum, int aim) {
        if (i == arr.length) {
            return sum == aim;
        }
        return existArrSumAim(arr, i + 1, sum, aim) || existArrSumAim(arr, i + 1, sum + arr[i], aim);
    }

    @Test
    public void test() {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        System.out.println("exist:" + existArrSumAim(arr, 2));
    }


}
