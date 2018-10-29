package com.yht.nowcode.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 有一个String[] ,请按照某种方式拼接成一个字符串，使得该字符串的ASCII值最小
 * 按照字典序将字符串排好，然后拼接就可以了
 *
 *  策略1：(不对)
 *      相同长度直接比较，不同长度，将较短的后面填充最小值，然后比较
 *      咋看是没问题的，但是："ba" , "b" 若按照之前的方式，得到b < ba  拼接的字符串为  "bba"  但实际最小的为"bab"
 *
 *  策略2：
 *      有两个字符串，若 字符串1+字符串2 < 字符串2+字符串1  字符串1排在前面
 *          否则字符串2排在前面
 *          类似于10进制数组合：12和32组合为1232实际的含义为12*10^2+32
 *
 */
public class LowestLexicography {
    private class MinStringComparator implements Comparator<String>{
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    public String getLexicographyString(String[] stringArr) {
        StringBuilder builder = new StringBuilder();
        Arrays.sort(stringArr, new MinStringComparator());
        for(String string : stringArr) {
            builder.append(string);
        }
        return builder.toString();
    }

}
