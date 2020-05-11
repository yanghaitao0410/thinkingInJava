package com.yht.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/3/29
 */
public class GenerateParenthesis {

    @Test
    public void test() {
        System.out.println(generateParenthesis2(3));
    }

    /**
     * 思路 ：
     *  将n对括号当作一个整体，然后每次将一对括号插入另一个括号中 最后去重
     *  例如：n = 2
     *  将"()" 插入到"()"中 一共有3个位置：
     *      左：()()
     *      中：(())
     *      右：()()
     *  最后去重
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        if(n == 0) {
            return list;
        }
        Set<String> set = new HashSet<>();
        String base = "()";
        for(int i = 0; i < n - 1; i++) {
            if(set.isEmpty()) {
                set.addAll(insertStr(base, base));
            } else {
                //比当前set多插入一对括号之后的集合
                Set<String> nextLevelSet = new HashSet<>();
                for(String str : set) {
                    nextLevelSet.addAll(insertStr(str, base));
                }
                set = nextLevelSet;
            }
        }
        list.addAll(set);
        return list;
    }

    /**
     * 返回str2插入到str1中之后的括号
     * @param str1
     * @param str2
     * @return
     */
    private Set<String> insertStr(String str1, String str2) {
        Set<String> set = new HashSet<>();
        //先把前后位置放进集合中
        set.add(str1.concat(str2));
        set.add(str2.concat(str1));
        for(int i = 0; i < str1.length() - 1; i++) { //在str1中间插空 一共length - 1个空
            set.add(str1.substring(0, i + 1).concat(str2).concat(str1.substring(i + 1)));
        }
        return set;
    }

    /**
     * 官方回溯法：
     *  在序列仍然保持有效时才添加 '(' or ')'，而不是像暴力法那样每次添加。我们可以通过跟踪到目前为止放置的左括号和右括号的数目来做到这一点，
     *
     * 如果我们还剩一个位置（表示一对括号），我们可以开始放一个左括号。 如果close < open 表示还没有设置右括号，我们可以放一个右括号。
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis2(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    /**
     *
     * @param ans 最终返回list
     * @param cur 当前已经拼接好的字符串
     * @param open 当前已设置左括号的个数
     * @param close 当前已设置右括号的个数
     * @param max
     */
    public void backtrack(List<String> ans, String cur, int open, int close, int max){
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        //左括号还没有到达最终数量 先拼接左括号，然后open增1，递归调用
        if (open < max)
            backtrack(ans, cur+"(", open+1, close, max);

        //右括号比左括号少 要拼接右括号 然后close增1 ，递归调用
        if (close < open)
            backtrack(ans, cur+")", open, close+1, max);
    }
}
