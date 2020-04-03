package com.yht.nowcode.array;

/**
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * @author yht
 * @create 2018/12/3
 */
public class ReplaceSpace {

    /**
     * 在字符串尾部填充任意字符，使得字符串的长度等于替换之后的长度。
     * 因为一个空格要替换成三个字符（%20），因此当遍历到一个空格时，需要在尾部填充两个任意字符。
     *
     * 令 P1 指向字符串原来的末尾位置，P2 指向字符串现在的末尾位置。
     * P1 和 P2 从后向前遍历，当 P1 遍历到一个空格时，就需要令 P2 指向的位置依次填充 02%（注意是逆序的），否则就填充上 P1 指向字符的值。
     *
     * 从后向前遍是为了在改变 P2 所指向的内容时，不会影响到 P1 遍历原来字符串的内容。
     * @param str
     * @return
     */
    public static String replaceSpace(StringBuffer str) {
        //开头结尾的空格没办法替换
//        StringBuffer result = new StringBuffer();
//        String[] stringArr = str.toString().split(" ");
//        int length = stringArr.length;
//        for(int i = 0; i < length ;i++) {
//            result.append(stringArr[i]);
//            if(i < length - 1) {
//                result.append("%20");
//            }
//        }
//        return result.toString();

        int p1 = str.length() - 1; //填充空格之前的string长度
        for(int i = 0; i <= p1; i++) {
            if(str.charAt(i) == ' ') {
                str.append("  ");
            }
        }

        int p2 = str.length() - 1; //填充空格之后的string长度
        while (p1 >= 0 && p1 < p2) {
            char c = str.charAt(p1--);
            if(c == ' ') {
                str.setCharAt(p2--, '0');
                str.setCharAt(p2--, '2');
                str.setCharAt(p2--, '%');
            } else{
                str.setCharAt(p2--, c);
            }
        }
        return str.toString();

    }
}
