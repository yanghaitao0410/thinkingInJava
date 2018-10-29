package com.yht.nowcode.recursive;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 打印字符串的全部排列
 * 例如字符串”abc“
 * 打印结果：
 *      ”abc“
 *      "acb"
 *      "bac"
 *      "bca"
 *      "cab"
 *      "cba
 *  打印字符串"acc"
 *      "acc"
 *      "cac"
 *      "cca"
 *   思路：循环word length次，每次在word中任意选择一个字符交换到开始位置，作为打印字符的开头，递归选择下一位置的字符
 *   最后打印时判断当前字符是否打印过，没打印过才打印，然后加入到set中
 */
public class AllPermutation {

    public static void printAllPermutation(String word) {
        Set<String> set = new HashSet<>();
        printAllPermutation(set, word, 0);
    }

    private static void printAllPermutation(Set<String> set, String word, int index) {
        if(index > word.length() - 1) {
            if (!set.contains(word)) {
                System.out.printf("\"%s\"\n", word);
                set.add(word);
            }
        } else {
            for(int i = index; i < word.length(); i++) {
                word = swop(word, index, i);
                printAllPermutation(set, word, index + 1);
            }
        }
    }

    private static String swop(String word, int index1, int index2) {
        char[] chars = word.toCharArray();
        char temp = chars[index1];
        chars[index1] = chars[index2];
        chars[index2] = temp;
        return new String(chars);
    }

    @Test
    public void test() {
        printAllPermutation("acc");
        System.out.println("===============");
        printAllPermutation("abc");
    }
}
