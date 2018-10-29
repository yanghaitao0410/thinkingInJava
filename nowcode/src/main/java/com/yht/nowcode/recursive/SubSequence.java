package com.yht.nowcode.recursive;

import org.junit.Test;

/**
 * 打印字符串的所有子序列，包括空字符串
 * 若字符串为"abc"
 * 则所有的子序列为：
 * "abc"
 * "a  "
 * " b "
 * "  c"
 * "ab "
 * " bc"
 * "a c"
 * "   "
 * 字符串的每个位置都有要或不要两种选择方式，确定当前的位置，递归处理
 */
public class SubSequence {

    public static void printSubSequence(String word) {
        printSubSequence(word, 0, true, "");
        printSubSequence(word, 0, false, "");
    }

    /**
     * @param word    原单词
     * @param index   遍历到单词的索引
     * @param getChar 是否要index位置的字符  true 要  false 不要
     * @param val     遍历结果
     */
    private static void printSubSequence(String word, int index, boolean getChar, String val) {
        int len = word.length();
        StringBuilder builder = new StringBuilder(val);
        if (index > len - 1) {
            return;
        } else if(index <= len - 1){
            if(index == 0) { //开始拼接"
                builder.append("\"");
            }
            if (getChar) {
                builder.append(word.charAt(index));
            } else {
                builder.append(" ");
            }
            if(index == len - 1) { //结束拼接"
                builder.append("\"");
                System.out.println(builder.toString());
                return;
            }
            index++;
            printSubSequence(word, index, true, builder.toString());
            printSubSequence(word, index, false, builder.toString());
        }
    }

    @Test
    public void test() {
        printSubSequence("abc");
    }
}
