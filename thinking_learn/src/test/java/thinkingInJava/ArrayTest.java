package thinkingInJava;

import java.util.Arrays;

/**
 * @Desc
 * @Author water
 * @date 2021/1/9
 **/
public class ArrayTest {

    public static void main(String[] args) {
        Arrays.stream(countBits(5)).forEach(System.out::println);
    }

    public static int[] countBits(int num) {
        int [] dp = new int[num + 1];
        dp[0] = 0;
        for (int i = 1; i <= num; i++) {
            int curIndex = i&1;
            int preCount = dp[i>>1];
            dp[i] = curIndex + preCount;
        }
        return dp;
    }
}
