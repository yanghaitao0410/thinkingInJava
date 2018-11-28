package com.yht.nowcode.array;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * @author yht
 * @create 2018/11/23
 */
public class Kmp {

    /**
     * 假设有两个字符串：text keyWord   ,若text中包含keyWord的子串，返回text中keyWord开始的位置
     * 例：abc123def 123d 返回3
     *
     * @param text
     * @param keyWord
     * @return
     */
    public int getIndexOf(String text, String keyWord) {
        if(text == null || text.length() < 1 || keyWord == null || keyWord.length() < 1){
            return -1;
        }
        int textIndex = 0;
        int keyIndex = 0;
        Integer[] subStringArr = getSubStringArr(keyWord);
        while (textIndex < text.length() && keyIndex < keyWord.length()) {
            if(text.charAt(textIndex) == keyWord.charAt(keyIndex)) {
                textIndex++;
                keyIndex++;
            } else if(subStringArr[keyIndex] != -1){ //keyIndex 不在keyWork第一个位置
                //前一个位置的最长前缀
                keyIndex = subStringArr[keyIndex]; //跳到最长前缀的下一个位置
            } else{
                textIndex++;
            }
        }
        return keyIndex == keyWord.length() ? textIndex - keyIndex : -1;
    }

    /**
     * 返回字符串每个位置的最长前缀长度
     *
     * @param word
     * @return
     */
    private Integer[] getSubStringArr(String word) {
        int length = word.length();
        Integer[] subStringArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            int compareIndex = 0;
            if (i == 0) {
                subStringArr[i] = -1; //定义当前位置前面没有字符，前缀长度为-1
            } else if (i == 1) {
                subStringArr[i] = 0;
            } else {
                //word[当前位置前一个位置的长度] word[i - 1]比
                //若相等，i位置长度 = i- 1 位置长度+1
                compareIndex = subStringArr[i - 1];
                if (word.charAt(compareIndex) == word.charAt(i - 1)) {
                    subStringArr[i] = compareIndex + 1;
                } else {
                    //若不等，获取word[subStringArr[subStringArr[i - 1]]] 和word[i - 1]
                    while (compareIndex > 0) {
                        compareIndex = subStringArr[compareIndex];
                        if (word.charAt(compareIndex) == word.charAt(i - 1)) {
                            subStringArr[i] = compareIndex + 1;
                            break;
                        }
                    }
                    if(subStringArr[i] == null) {
                        subStringArr[i] = 0;
                    }
                }
            }
        }
        return subStringArr;
    }

    private Integer[] getSubStringArr2(String word) {
        if(word.length() == 1) {
            return new Integer[]{-1};
        }
        Integer[] next = new Integer[word.length()];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0; //最长前缀的长度
        while(i < next.length) {
            if(word.charAt(i - 1) == word.charAt(cn)) {
                next[i++] = ++cn;
            } else if(cn > 0){
                cn = next[cn]; //跳到前一个最长前缀的下一个位置 得到长度
            } else{
                next[i++] = 0;
            }
        }
        return next;
    }

    @Test
    public void testGetSubStringArr () {
        Stream.of(getSubStringArr("ababcababtk")).forEach(x -> System.out.printf("%d ", x));
    }

    @Test
    public void testKmp() {
        System.out.println(getIndexOf("abeabcdd", "abc"));
    }

}
