package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 *
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 *
 * 示例 1:
 *
 * 输入: "III"
 * 输出: 3
 * 示例 2:
 *
 * 输入: "IV"
 * 输出: 4
 * 示例 3:
 *
 * 输入: "IX"
 * 输出: 9
 * 示例 4:
 *
 * 输入: "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * 示例 5:
 *
 * 输入: "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *
 * @author yht
 * @create 2020/3/22
 */
public class IntIntoRoman {

    @Test
    public void test() {
        Assert.assertEquals("III", intToRoman(3));
        Assert.assertEquals("IV", intToRoman(4));
        Assert.assertEquals("IX", intToRoman(9));
        Assert.assertEquals("LVIII", intToRoman(58));
        Assert.assertEquals("MCMXCIV", intToRoman(1994));
    }

    public String intToRoman(int num) {
        if(num < 1 || num > 3999) {
            return "";
        }
        Map<Integer, String> map = new LinkedHashMap<>();

        map.put(1000, "M");
        map.put(500, "D");
        map.put(100, "C");
        map.put(50, "L");
        map.put(10, "X");
        map.put(5, "V");
        map.put(1, "I");
        StringBuilder builder = new StringBuilder();

        int bit = 1000;
        while (bit >= 1) {
            String roman = getSingle(num, bit, map);
            if (!roman.isEmpty()) {
                builder.append(roman);
                num %= bit; //转换最高位成功后，将最高位去掉，继续转换下面的数字
            }
            bit /= 10; //进位向下移动一格
        }

        return builder.toString();

    }

    /**
     * 把最高位数字转换为罗马数字
     * @param num 数值
     * @param bit 进位 个位1 十位10 百位100 千位 1000
     * @param map 罗马数字映射map
     * @return
     */
    private String getSingle(int num, int bit, Map<Integer, String> map) {
        StringBuilder builder = new StringBuilder();
        int mCount = num / bit;
        while(mCount > 0) {
            if(mCount < 4) {
                for(int i = 0; i < mCount; i++) {
                    builder.append(map.get(bit));
                }
                mCount = 0;
            } else if(mCount == 4) {
                builder.append(map.get(bit)).append(map.get(bit * 5));
                mCount = 0;
            } else if(mCount > 4 && mCount < 9) {
                builder.append(map.get(bit* 5));
                mCount -= 5;
            } else { //=9
                builder.append(map.get(bit)).append(map.get(bit * 10));
                mCount = 0;
            }
        }

        return builder.toString();
    }
}
