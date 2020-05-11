package com.yht.nowcode.recursive;

import java.util.HashMap;
import java.util.Map;

/**
 * 母牛每年生一只母牛，新出生的母牛成长3年后也能每年生一只母牛，假设不会死。
 * 求N年后，母牛的数量。
 * 分析过程：
 * 年份 1    2     3     4    5    6     7
 *     A    A     A     A    A    A     A
 *          B     B     B    B    B     B
 *                C     C    C    C     C
 *                      D    D    D     D
 *                           E    E     E
 *                           F    F     F
 *                                G     G
 *                                H     H
 *                                I     I
 *                                      J
 *                                      K
 *                                      L
 *                                      M
 *数量 1     2    3     4    6    9
 * 分析得出：
 *    第n年的牛等于 n-1年全部的牛 + 以成熟的母牛生的新母牛
 *    第5年开始：
 *    F(N) = F(N-1) + F(N-3)
 *
 * 进阶：
 *
 *  假设每只母牛只能活10年，求N年后，母牛的数量
 *   F(N) = F(N-1) + F(N-3) - F(N-10)
 *  10年前和之前一样
 *  第11年母牛数量=第10年全部数量+第8年全部数量- 10年前的牛
 */
public class CattleAmount {
    static Map<Integer, Long> amountYearMap;
    public CattleAmount(){
        amountYearMap = new HashMap<>();
    }

    public static long getCattleAmount(int N) {
        if(N < 0) {
            return 0;
        }
        if(N <= 4) {
            return N;
        }
        return getAmount(N - 1) + getAmount(N - 3);
    }

    private static long getAmount(int year) {
        Long amount = amountYearMap.get(year);
        if(amount == null) {
            amount = getCattleAmount(year);
            amountYearMap.put(year, amount);
        }
        return amount;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        System.out.printf("Cattle Amount: %d", getCattleAmount(6));
        System.out.printf("计算花费%s毫秒", System.currentTimeMillis() - start);
    }
}
