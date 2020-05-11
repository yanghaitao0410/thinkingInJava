package com.yht.nowcode.recursive;

public class Factorial {

    /**
     * 获取n的阶乘非递归版
     * @param n
     * @return
     */
    public static long getFactorial1(int n) {
        long result = 1L;
        for(int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * 获取n的阶乘递归版
     * @param n
     * @return
     */
    public static long getFactorial2(int n) {
        if(n == 1) {
            return 1L;
        }
        return n * getFactorial2(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(getFactorial1(n));
        System.out.println(getFactorial2(n));
    }
}
