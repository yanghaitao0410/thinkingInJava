package com.yht.nowcode.string;

/**
 * 翻转单词顺序列
 * 传入 "I am a student."
 * 返回 "student. a am I"
 *
 * @author yht
 * @create 2018/12/10
 */
public class ReverseSentences {

    /**
     * 时间复杂度O(n) 空间复杂度O(n)
     *
     * @param str
     * @return
     */
    public String reverseSentence1(String str) {
        if (str == null) {
            return null;
        }
        String[] strs = str.split(" ");
        if (strs.length == 0) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = strs.length - 1; i >= 0; i--) {
            builder.append(strs[i]);
            if (i > 0) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * 时间复杂度O(n) 空间复杂度O(1)
     * 传入 "I am a student."
     * 翻转两次
     * 先翻转每个单词
     *  I ma a .tneduts
     *  翻转整个句子
     *  student. a am I
     *
     * @param str
     * @return
     */
    public static String reverseSentence2(String str) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        int length = chars.length;
        //start 单词开始位置 end 单词结束位置
        int start = 0, end = 0;
        while (end <= length) {
            //遇到空格或最后一个单词 都需要翻转
            if (end == length || chars[end] == ' ') {
                reverseString(chars, start, end - 1);
                start = end + 1;
            }
            end++;
        }
        reverseString(chars, 0, length - 1);
        return new String(chars);
    }

    /**
     * 翻转char[] 从开始位置到结束位置的值
     * @param chars
     * @param start
     * @param end
     */
    private static void reverseString(char[] chars, int start, int end) {
        while (start < end) {
            swop(chars, start++, end--);
        }
    }

    private static void swop(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    /**
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
     *
     * 思路：先将 abc和 XYZdef分别翻转，然后再把整个字符串翻转
     */
    public static String leftRotateString(String str,int n) {
        if(n >= str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        reverseString(chars, 0, n - 1);
        reverseString(chars, n, chars.length - 1);
        reverseString(chars, 0, chars.length - 1);

        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(reverseSentence2("I am a student."));
    }

}
