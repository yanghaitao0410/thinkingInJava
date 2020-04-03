package com.yht.nowcode.basicstructure;

/**
 * 找到一个字符串中最长回文子串
 */
public class Manacher {

    /**
     * 暴力方法得到字符串中最长回文子串
     * 将字符串中每个字符i当作回文的中心向两边扩，本身回文长度1，若i-1 位置和i+1位置相等，回文长度就变为了3，依次类推
     *
     * @param str
     * @return
     */
    public static String getLongestPlalindrome(String str) {
        String specialStr = addSpecialChar(str);
        int specialStrLength = specialStr.length(),
                max = 0; //最长回文长度
        String resultStr = specialStr.substring(0, 1); //最长回文子串
        int[] lengthArr = new int[specialStrLength]; //存储各个位置回文长度

        lengthArr[0] = 1;
        for (int i = 1; i < specialStrLength; i++) {
            int plalindromeLength = 1;
            for (int j = i + 1; j < specialStrLength; j++) {
                int prej = i - (j - i); //以i为中心j的对称位置
                if(prej >= 0 && specialStr.charAt(j) == specialStr.charAt(prej)) {
                    plalindromeLength += 2;
                } else { //比对不上 存储之前长度
                    lengthArr[i] = plalindromeLength;
                    if(max < plalindromeLength) {
                        max = plalindromeLength;
                        resultStr = specialStr.substring(j - plalindromeLength, j);
                    }
                    break;
                }
            }
        }
        return resultStr.replaceAll("#", "");
    }




    /**
     * 将字符串每个位置的前中后添加特殊字符#
     * 为了得到偶回文的长度
     * 例如abc123
     * 返回 #a#b#c#1#2#3#
     *
     * @param str
     * @return
     */
    private static String addSpecialChar(String str) {
        StringBuilder resultbuilder = new StringBuilder("#");
        for (int i = 0; i < str.length(); i++) {
            resultbuilder.append(str.charAt(i)).append("#");
        }
        return resultbuilder.toString();
    }

    public static void main(String[] args)  {
//        System.out.println(addSpecialChar("abc123"));
        System.out.println(getLongestPlalindrome("tfabaktkabafs"));
    }
}
