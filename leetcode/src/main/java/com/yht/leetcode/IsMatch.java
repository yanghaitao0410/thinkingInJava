package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yht
 * @create 2020/3/15
 */
public class IsMatch {

    @Test
    public void test() {
        Assert.assertEquals(false , isMatch3("ab", "a*"));
        Assert.assertEquals(true , isMatch3("aab", "c*a*b"));
        Assert.assertEquals(true , isMatch3("ab", ".*"));
        Assert.assertEquals(true , isMatch3("aa", "a*"));
    }

    public boolean isMatch(String text, String pattern) {

        if (pattern.isEmpty()) return text.isEmpty();
        boolean first_match = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*'){
            return (isMatch(text, pattern.substring(2)) ||
                    (first_match && isMatch(text.substring(1), pattern)));
        } else {
            return first_match && isMatch(text.substring(1), pattern.substring(1));
        }
    }

    /**
     * 官方回溯法
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch2(String s, String p) {
        //step1 判断p是否是空串
        if(p.isEmpty()) {
            return s.isEmpty();
        }

        //step2 字符串第一位进行匹配 s是否为空在这里进行判断
        boolean firstMatch = (!s.isEmpty() && (s.charAt(0) == p.charAt(0)) || (p.charAt(0) == '.'));

        //step3 第二位判断是否匹配

        //step3.1 看p是否第二位是*
        if(p.length() >= 2 && p.charAt(1) == '*') {

            //p第二位是* 首先判断去掉*前面的字符串,是否可以匹配s; 若不能去掉，则s向下移动一位递归判断 因为*表示前一位的0到多次 所以p不需要进行移动
            return (isMatch(s, p.substring(2)) || (firstMatch && isMatch(s.substring(1), p)));

        } else { //不是* s和p都向下移动一位递归判断
            return firstMatch && isMatch(s.substring(1), p.substring(1));
        }
    }

    private boolean dp(int sIndex, int pIndex, String s, String p, Boolean[][] dpArr) {
        if(dpArr[sIndex][pIndex] != null) {
            return dpArr[sIndex][pIndex];
        }
        boolean res ;
        //step1 判断p是否是空串
        if(pIndex == p.length()) {
            res =  sIndex == s.length();
        } else {
            //step2 字符串第一位进行匹配 s是否为空在这里进行判断
            boolean firstMatch = ((sIndex < s.length()) && (p.charAt(pIndex) == s.charAt(sIndex))|| (p.charAt(pIndex) == '.'));

            //step3 第二位判断是否匹配
            //step3.1 看p是否第二位是*
            if(pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
                //p第二位是* 首先判断去掉*前面的字符串,是否可以匹配s; 若不能去掉，则s向下移动一位递归判断 因为*表示前一位的0到多次 所以p不需要进行移动
                res = (dp(sIndex, pIndex+ 2, s, p, dpArr) || (firstMatch && dp(sIndex + 1, pIndex, s, p, dpArr)));

            } else { //不是* s和p都向下移动一位递归判断
                res = firstMatch && dp(sIndex + 1, pIndex + 1, s, p, dpArr);
            }
        }
        dpArr[sIndex][pIndex] = res;
        return res;
    }

    /**
     * 官方动态规划算法
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch3(String s, String p) {
        //存储s中[i, s.length)，p中[j, p.length)，的字符串是否匹配
        Boolean[][] dpArr = new Boolean[s.length() + 1][p.length() + 1];
        return dp2(0, 0, s, p, dpArr);
    }

    private boolean dp2 (int i, int j, String text, String pattern, Boolean[][] memo) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        boolean ans;
        if (j == pattern.length()){
            ans = i == text.length();
        } else{
            boolean first_match = (i < text.length() &&
                    (pattern.charAt(j) == text.charAt(i) ||
                            pattern.charAt(j) == '.'));

            if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                ans = (dp(i, j+2, text, pattern, memo) ||
                        first_match && dp2(i+1, j, text, pattern, memo));
            } else {
                ans = first_match && dp2(i+1, j+1, text, pattern, memo);
            }
        }
        memo[i][j] = ans;
        return ans;
    }

}
