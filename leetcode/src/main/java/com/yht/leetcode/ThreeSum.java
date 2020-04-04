package com.yht.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例：
 *
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 * @author yht
 * @create 2020/3/23
 */
public class ThreeSum {

    @Test
    public void test() {

        Integer[] nums = Stream.of(-1, 0, 1, 2, -1, -4).toArray(Integer[]::new);
        List<List<Integer>> result = threeSum2(nums);
        result.stream().forEach(System.out::println);
    }

    /**
     * 方法一：暴力法
     * 排好序后查找，遇到重复数字跳过去重
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum1(Integer[] nums) {
        List<List<Integer>> threeSumList = new ArrayList<>();
        if(nums.length == 0)
            return threeSumList;
        List<Integer> list = Stream.of(nums).collect(Collectors.toList());
        Collections.sort(list);
        nums = list.toArray(nums);
        if (nums[0] > 0) {
            return threeSumList;
        }
        for(int a = 0; a < nums.length - 2; a++) {
            if(a > 0 && nums[a] == nums[a - 1])
                continue;
            for(int b = a + 1; b  < nums.length - 1; b++) {
                if(b > a + 1 && nums[b] == nums[b - 1])
                    continue;
                for(int c = b + 1; c < nums.length; c++) {
                    if(c > b + 1 && nums[c] == nums[c - 1])
                        continue;
                    if(nums[a] + nums[b] + nums[c] == 0) {
                        threeSumList.add(Stream.of(nums[a], nums[b], nums[c]).collect(Collectors.toList()));
                    }
                }
            }
        }
        return threeSumList;
    }

    /**
     * 双指针法
     * 排好序后 nums[0] > 0 直接返回空
     * 去重方法和上面一样
     * 遍历nums得到a ，b从a+1处开始向下遍历  c从末端开始往回遍历
     * a+ b+ c == 0 添加到list
     * a+ b + c <0 说明太小 b向下移动
     * a+ b + c >0 说明太大 c向上移动
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(Integer[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums.length == 0) {
            return result;
        }
        List<Integer> list = Stream.of(nums).collect(Collectors.toList());
        Collections.sort(list);
        nums = list.toArray(nums);

        if (nums[0] > 0) {
            return result;
        }
        for(int a = 0; a < nums.length - 2; a++) {
            if(a > 0 && nums[a] == nums[a - 1])
                continue;
            int b = a + 1;
            int c = nums.length - 1;
            while(b < c) {
                if(nums[a] + nums[b] + nums[c] == 0) {
                    result.add(Stream.of(nums[a], nums[b], nums[c]).collect(Collectors.toList()));
                    while(b < c &&  nums[b] == nums[b + 1])
                        b++;
                    while (b < c &&  nums[c] == nums[c - 1])
                        c--;
                    b++;
                    c--;
                } else if(nums[a] + nums[b] + nums[c] >= 0) {
                    c--;
                } else {
                    b++;
                }
            }
        }
        return result;
    }

}
