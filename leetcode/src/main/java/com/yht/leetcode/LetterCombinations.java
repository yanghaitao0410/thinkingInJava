package com.yht.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *      2:abc
 *      3:def
 *      4:ghi
 *      5:jkl
 *      6:mno
 *      7:pqrs
 *      8:tuv
 *      9:wxyz
 *
 *
 * 示例:
 *
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 说明:
 * 尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author yht
 * @create 2020/3/24
 */
public class LetterCombinations {

    @Test
    public void test() {
        System.out.println(letterCombinations("23"));
    }

    private Map<String, String> map = new HashMap<>();
    {
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
    }
    private List<String> result = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if(digits.length() == 0) {
            return result;
        }
        findCombination(digits, 0, "");
        return result;
    }

    /**
     * 拼接digits字符串中 index处数字对应的字母 ，然后将拼接后的字符串item设置到result中
     * @param digits 输入数字字符串
     * @param index 当前遍历到字符串的索引
     * @param item 要设置的字符串
     */
    private void findCombination(String digits, int index, String item) {
        if(index == digits.length()) { //整个digits已经全部遍历完成 将item设置到result中
            result.add(item);
            return;
        }
        String num = digits.substring(index, index + 1);
        String letters = map.get(num);
        for(int i = 0; i < letters.length(); i++) {
            String letter = letters.substring(i, i + 1); //取出当前数字对应的每一个字母，然后把这个字母追加到item中，然后递归拼接下一个数字中的字符串
            findCombination(digits, index + 1, item.concat(letter));
        }
    }
}
