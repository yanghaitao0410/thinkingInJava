package com.yht;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
//        int[] nums = new int[]{1, 2};
//        LinkedList<Integer> list = new LinkedList<>(Arrays.stream(nums).boxed().collect(Collectors.toList()));

//        System.out.println(isAdditiveNumber("121474836472147483648"));
        List<Integer> list1 = Stream.of(2).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(3, 4).collect(Collectors.toList());
        List<Integer> list3 = Stream.of(6, 5, 7).collect(Collectors.toList());
        List<Integer> list4 = Stream.of(4, 1, 8, 3).collect(Collectors.toList());
        System.out.println(minimumTotal(Stream.of(list1, list2, list3, list4).collect(Collectors.toList())));

    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int row = triangle.size();
        int col = triangle.get(row - 1).size();
        int[][]dp = new int[row][col];
        //当前行有几个元素
        int size = 1,
                result = Integer.MAX_VALUE;

        dp[0][0] = triangle.get(0).get(0);
        for(int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
            if (i == row - 1) {
                result = Math.min(result, dp[i][0]);
                result = Math.min(result, dp[i][i]);
            }
        }

        for(int i = 2; i < row; i++) {
            for (int j = 1; j <= size; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle.get(i).get(j);
                if (i == row - 1) {
                    result = Math.min(result, dp[i][j]);
                }
            }
            size++;
        }
        return result;
    }
}
