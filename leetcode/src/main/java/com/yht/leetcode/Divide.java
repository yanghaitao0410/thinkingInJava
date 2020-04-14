package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 *
 * 返回被除数 dividend 除以除数 divisor 得到的商。
 *
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 *
 *  
 *
 * 示例 1:
 *
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 * 示例 2:
 *
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 *  
 *
 * 提示：
 * 被除数和除数均为 32 位有符号整数。
 * 除数不为 0。
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2^31,  2^31 − 1]。本题中，如果除法结果溢出，则返回 2^31 − 1。
 *
 * @author yht
 * @create 2020/4/4
 */
public class Divide {

    @Test
    public void test() {
//        Assert.assertEquals(3, divide(10, 3));
//        Assert.assertEquals(-2, divide(7, -3));
//        Assert.assertEquals(1, divide(1, 1));
        Assert.assertEquals(-1073741824, divide2(-2147483648, 2));
    }

    /**
     * 暴力法 看看除数中有几个被除数
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide(int dividend, int divisor) {
        if(dividend == 0) return 0;
        if(divisor == 1) return dividend;
        if(divisor == -1){
            if(dividend>Integer.MIN_VALUE) return -dividend;// 只要不是最小的那个整数，都是直接返回相反数
            return Integer.MAX_VALUE;// 是最小的那个 转换为正数会越界，需要按照题目要求返回最大的整数
        }

        Integer divisorSum = 0, result = 0;
        boolean hasMinus = false;
        if(dividend < 0) {
            dividend = -dividend;
            hasMinus = !hasMinus;
        }
        if(divisor < 0) {
            divisor = -divisor;
            hasMinus = !hasMinus;
        }
        //考虑整除情况 该循环结束时，result会多加一
        while(divisorSum <= dividend) {
            divisorSum += divisor;
            result++;
        }

        if(result > 0) {
            result--;
        }

        if(hasMinus) {
            result = -result;
        }
        return result;
    }

    /**
     * 递归及如何提高效率快速逼近结果
     * 举个例子：11 除以 3 。
     * 首先11比3大，结果至少是1， 然后我让3翻倍，就是6，发现11比3翻倍后还要大，那么结果就至少是2了，那我让这个6再翻倍，得12，11不比12大，
     * 但是我知道最终结果肯定在2和4之间。也就是说2再加上某个数，这个数是多少呢？
     * 我让11减去刚才最后一次的结果6，剩下5，我们计算5是3的几倍，也就是除法，看，递归出现了。
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public int divide2(int dividend, int divisor) {
        if(divisor == -1 && dividend == Integer.MIN_VALUE) return Integer.MAX_VALUE; // 溢出
        int sign = 1;
        if((dividend > 0 && divisor < 0)||(dividend < 0 && divisor > 0))
            sign = -1;
        if(divisor == 1) return dividend;
        if(divisor == -1) return -dividend;
        int a = dividend>0 ? -dividend : dividend;
        int b = divisor>0 ? -divisor : divisor;
        // 都改为负号是因为int 的范围是[2^32, 2^32-1]，如果a是-2^32，转为正数时将会溢出
        //System.out.println(a + " " + b);
        if(a > b) return 0;
        int ans = div(a,b);
        return sign == -1 ? -ans : ans;
    }


    int div(int a, int b){  // 似乎精髓和难点就在于下面这几句
        if(a > b) return 0;
        int count = 1;
        int tb = b;
        while(tb+tb >= a && tb+tb < 0){ // 溢出之后不再小于0
            tb += tb;
            count += count;
            //System.out.println(tb + " " + count + " " + count*b);
        }
        return count+div(a-tb,b);
    }

}
