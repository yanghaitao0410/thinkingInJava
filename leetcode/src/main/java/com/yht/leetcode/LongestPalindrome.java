package com.yht.leetcode;

import javafx.util.Pair;
import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.util.Date;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author yht
 * @create 2020/3/8
 */
public class LongestPalindrome {

    @Test
    public void test() {
        Date start  = new Date();
//        System.out.println(longestPalindrome("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg"));
        System.out.println(longestPalindromeByMid("a"));
        Date end = new Date();
        System.out.printf("共耗时%s毫秒", end.getTime() - start.getTime());
    }

    /**
     * 逆序法 把字符串逆序 然后找出逆序后的相同子串 然后再判断该子串是不是回文 是直接返回 不是继续逆序该子串 重复上面过程
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        return partition(s);
    }

    /*
        递归找回文串
    */
    private String partition(String s) {
        String reverseStr = reverseString(s);
        if(isPalindrome(s)) {
            return s;
        }
        String simpleSubString = getLongestSimpleSubString(s, reverseStr);
        //如果simpleSubString = "" 表示逆序后字母一个都不相同 说明没有找到2个字母及以上回文 返回任意一个字母
        if(simpleSubString.equals("")) {
            return s.substring(0, 1);
        } else if(isPalindrome(simpleSubString)) {
            return simpleSubString;
        } else {
            return partition(simpleSubString);
        }
    }

    //判断传入字符串是不是回文
    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        for (; left < right; left++, right--) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
        }
        return true;
    }

    //返回两个字符串最长相同部分
    private String getLongestSimpleSubString(String s1, String s2) {
        Pair<String, Integer> pair = null;

        for(int s1Start = 0; s1Start < s1.length(); s1Start ++) {
            for(int s2Start = 0; s2Start < s2.length(); s2Start++) {
                if(s1.charAt(s1Start) == s2.charAt(s2Start)) {
                    int s1end = s1Start, s2end = s2Start;
                    while(s1end < s1.length() && s2end < s2.length() && s1.charAt(s1end) == s2.charAt(s2end)) {
                        s1end++;
                        s2end++;
                    }

                    String subStr = s1.substring(s1Start, s1end);
                    if(isPalindrome(subStr)) { //如果子串是回文，才进行比较和设值
                        int length = s1end - s1Start;
                        if(pair == null || pair.getValue() < length) {
                            pair = new Pair(s1.substring(s1Start, s1end), length);
                        }
                    }
                }
            }
        }
        if(pair != null) {
            return pair.getKey();
        } else {
            return "";
        }
    }

    //逆序字符串
    private String reverseString(String s) {
        char[] chars = s.toCharArray();
        int start = 0, end = chars.length-1;
        while(start < end) {
            swop(chars, start++, end--);
        }
        return new String(chars);
    }

    private void swop(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    //中心法 找出已i、或i+1为中心回文的长度 然后算出起始和结束位置索引 返回
    public String longestPalindromeByMid(String s) {
        if(s.equals("")) {
            return "";
        }
        int len = 0, start = 0;
        for(int i = 0; i < s.length(); i++) {
            int cur = Math.max(getLen(s, i, i), getLen(s, i, i + 1)); //返回以i作为中心点的奇回文和以i和i+1作为中心点的偶回文中最长的长度
            if(cur > len) {
                len = cur;
                start = i - (cur - 1) / 2; //通过回文长度及中心点位置，计算出起始位置 len - 1是为了在计算偶回文的时候不出现错误情况 例如i = 0 len长度为2 若不减一结果为-1
            }
        }
        return s.substring(start, start+len);
    }

    //返回已[left, right] 作为回文的中心点 最长的回文长度
    private Integer getLen(String s, int left, int right) {
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        //跳出循环之后 left和right的位置是越界的 计算长度应该减2
        return right - left + 1 - 2;
    }

}
