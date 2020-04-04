package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 *
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 * @author yht
 * @create 2020/3/23
 */
public class ThreeSumClosest {

    @Test
    public void test() {
        Assert.assertEquals(2, threeSumClosest2(new int[] {-1, 0, 1, 1, 55}, 3));
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums.length == 0) {
            return target;
        }
        if(nums.length < 3) {
            int arrSum = Arrays.stream(nums).sum();
            return arrSum > target ? arrSum - target : target - arrSum;
        }
        int result = 0;
        //任意3个数字只和和target的差距 固定为正值 一开始设置为Interge最大值
        int colsestNum = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 2; i++) {
            if(i > 0 && nums[i] == nums[i - 1])
                continue;
            for(int j = i + 1; j < nums.length - 1; j++) {
                if(j > i + 1 && nums[j] == nums[j - 1])
                    continue;
                for(int k = j + 1; k < nums.length; k++) {
                    if(k > j + 1 && nums[k] == nums[k - 1])
                        continue;
                    int threeSum = nums[i] + nums[j] + nums[k];
                    int curDiffentValue = threeSum > target ? threeSum - target : target - threeSum;
                    if(curDiffentValue < colsestNum) {
                        colsestNum = curDiffentValue;
                        result = threeSum;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 在数组 nums 中，进行遍历，每遍历一个值利用其下标i，形成一个固定值 nums[i]
     * 再使用前指针指向 start = i + 1 处，后指针指向 end = nums.length - 1 处，也就是结尾处
     * 根据 sum = nums[i] + nums[start] + nums[end] 的结果，判断 sum 与目标 target 的距离，如果更近则更新结果 ans
     * 同时判断 sum 与 target 的大小关系， 因为最终目的是找出和target差距最小的和
     * 因为数组有序，
     *   如果 sum > target 则 end--，
     *   如果 sum < target 则 start++，
     *   如果 sum == target 则说明距离为 0 直接返回结果
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest2(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = nums[0] + nums[1] + nums[2];
        //不能跳过相同数字 因为是要找出和target相差最小的3个数的和
        for (int i = 0; i < nums.length; i++) {
            int start = i + 1, end = nums.length - 1;
            while (start < end) {
                int sum = nums[start] + nums[end] + nums[i];
                if (Math.abs(target - sum) < Math.abs(target - ans))
                    ans = sum;
                if (sum > target)
                    end--;
                else if (sum < target)
                    start++;
                else
                    return ans;
            }
        }
        return ans;
    }
}
