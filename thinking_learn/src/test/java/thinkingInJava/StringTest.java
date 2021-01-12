package thinkingInJava;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class StringTest {

    @Test
    public void test() {
        String s1 = "Programming";

        String s2 = new String("Programming");

        String s3 = "Program" + "ming";

        System.out.println(s1 == s2); //false

        System.out.println(s1 == s3); //true

        System.out.println(s1 == s1.intern()); //true
    }

    public static void main(String[] args) {
        System.out.println(wordBreak("leetcode", Arrays.asList("let", "code")));
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        boolean [][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                String subs = s.substring(i, j + 1);
                dp[i][j] = wordDict.contains(subs);
            }
        }

        return process(dp, 0, 0);
    }

    public static boolean process(boolean [][] dp, int row, int col) {
        //只有前面的为true才会增加row和col向下遍历 ，超出数组范围说明找到了
        if (row >= dp.length || col >= dp[0].length) {
            return true;
        }
        //遍历列
        for (int j = col; j < dp[0].length; j++) {
            if (dp[row][j]) {
                boolean result = process(dp, j + 1, j + 1);
                if (result) {
                    return true;
                }
            }
        }

        return false;
    }
}


