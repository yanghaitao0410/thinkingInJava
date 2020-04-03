package com.yht.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 *
 * 示例 3:
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2019/8/24
 */
public class LengthOfLongestSubstring {

    /**
     * 暴力法
     * 遍历所有位置的最长无重复子串，每次更新最大长度
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int longestSubStringLength = 0;
        if(s == null || "".equals(s)) {
            return longestSubStringLength;
        }
        Set<Character> stringSet = new HashSet<>();
        for(int i = 0; i < s.length(); i++) {
            stringSet.clear();
            stringSet.add(s.charAt(i));
            for(int j = i + 1; j < s.length(); j++) {
                if(stringSet.contains(s.charAt(j))){
                    break;
                } else {
                    stringSet.add(s.charAt(j));
                }
            }
            longestSubStringLength = Math.max(longestSubStringLength, stringSet.size());
        }
        return longestSubStringLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

}
