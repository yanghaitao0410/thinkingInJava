package com.yht.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？
 * 找出所有满足条件且不重复的四元组。
 *
 * 注意：
 * 答案中不可以包含重复的四元组。
 *
 * 示例：
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 *
 * 满足要求的四元组集合为：
 * [
 *   [-1,  0, 0, 1],
 *   [-2, -1, 1, 2],
 *   [-2,  0, 0, 2]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author yht
 * @create 2020/3/26
 */
public class FourSum {

    @Test
    public void test() {
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
    }

    /**
     * 在3数之和的基础上多加一层循环
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums.length < 4) {
            return result;
        }
        Arrays.sort(nums);

        for(int a = 0; a < nums.length - 3; a++) {
            if(a > 0 && nums[a] == nums[a - 1]){
                continue;
            }
            for(int  b = a + 1; b < nums.length - 2; b++) {
                if(b > a + 1 && nums[b] == nums[b - 1]){
                    continue;
                }
                int c = b + 1, d = nums.length - 1;
                while (c < d) {
                    int sum = nums[a] + nums[b] + nums[c] + nums[d];
                    if(sum == target) {
                        List<Integer> fourNumList = Stream.of(nums[a], nums[b], nums[c], nums[d]).collect(Collectors.toList());
                        result.add(fourNumList);
                        while(c < d && nums[c] == nums[c + 1]) {
                            c++;
                        }
                        while ((c < d && nums[d] == nums[d - 1])) {
                            d--;
                        }
                        c++;
                        d--;
                    } else if(sum < target) {
                        c++;
                    } else {
                        d--;
                    }
                }
            }
        }
        return result;
    }
}
