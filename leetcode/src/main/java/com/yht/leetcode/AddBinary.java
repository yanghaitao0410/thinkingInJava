package com.yht.leetcode;


/**
 * 给定两个二进制字符串，返回他们的和（用二进制表示）。
 *
 * 输入为非空字符串且只包含数字 1 和 0。
 *
 * 示例 1:
 *
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例 2:
 *
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-binary
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author yht
 * @create 2020/1/5
 */
public class AddBinary {

    public static void main(String[] args) {
        System.out.println(addBinary("11111", "11111"));
    }

    public static String addBinary(String a, String b) {
        boolean needCarry = false;
        int alen = a.length(), blen = b.length();
        int maxLen = Math.max(alen, blen), minLen = Math.min(alen, blen);
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i <= maxLen - minLen; i++) {
            builder.append("0");
        }
        if(maxLen == alen) {
            builder.append(b);
            b = builder.toString();
        } else {
            builder.append(a);
            a = builder.toString();
        }
        char[] chars = new char[maxLen + 1];

        for(int i = maxLen - 1; i >= 0; i--) {
            char ac = a.charAt(i);
            char bc = b.charAt(i);
            if(ac == '1' && bc == '1') { //都为1的情况
                if(needCarry) { //需要进位
                    chars[i + 1] = '1';   //设定的char数组的长度比两个字符串中较长的那个还长1 所以对应的位置应为i+1
                } else { //不需要进位
                    chars[i + 1] = '0';
                }
                needCarry = true;
            } else if(ac == '0' && bc == '0') { //都为0的情况
                if(needCarry) {
                    chars[i + 1] = '1';
                } else {
                    chars[i + 1] = '0';
                }
                needCarry = false;
            } else { //一个是1一个是0的情况
                if(needCarry) {
                    chars[i + 1] = '0';
                    needCarry = true;
                } else {
                    chars[i + 1] = '1';
                    needCarry = false;
                }
            }
        }

        if(needCarry) { //把长字符串剩余的字符补到char数组中
            chars[0] = '1';
            return new String (chars);
        } else{
            return new String(chars).substring(1);
        }
    }
}
