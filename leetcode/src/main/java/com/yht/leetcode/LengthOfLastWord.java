package com.yht.leetcode;

/**
 * @author yht
 * @create 2019/12/29
 */
public class LengthOfLastWord {

    public static int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) //空字符串返回0
            return 0;
        char[] charArr = s.toCharArray();
        int index = 0, spaceSize = 0;
        for (int i = 0; i < charArr.length; i++) {
            //i位置没有遍历到charArr.length - 2 并且i+1位置是字母 并且当前位置是空格
            if (charArr[i] == ' ' && i < charArr.length - 1 && charArr[i + 1] != ' ') {
                index = i;
                spaceSize++;
            }
        }
        boolean canAdd = true;
        int endSpaceSize = 0;
        for (int i = charArr.length - 1; i >= 0; i--) { //确定最后的空格数量
            if (charArr[i] == ' ' && canAdd) {
                endSpaceSize++;
                spaceSize++;
            } else {
                canAdd = false;
            }
        }

        if(index == 0 && spaceSize == 0) { //没有空格的情况 单词长度即为总长度
            return charArr.length;
        }

        if(endSpaceSize == charArr.length)  //全是空格的情况 返回0
            return 0;

        //若是string中间有空格 总长度 - 最后一个单词前的空格 - 1（因为单词不算空格） - 最后的空格长度
        //string中间没空格 总长度 - 最后的空格长度
        return index == 0 ? charArr.length - endSpaceSize : charArr.length - index - 1 - endSpaceSize;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("a "));
    }

}
