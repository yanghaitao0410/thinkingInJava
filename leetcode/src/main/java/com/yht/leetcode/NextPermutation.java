package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * 获取给定数字序列,在字典序中的下一个排列；
 * 如果不存在下一个更大的排列,即当前序列已经是字典序中的最大序列，则将数字重新排列成最小的序列（即升序排列）。
 * <p>
 * 要求：
 * 必须在原数组中修改，只允许使用额外常数空间。
 * <p>
 * 以下是一些例子，输入是左侧序列，输出是右侧序列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-permutation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/4/7
 */
public class NextPermutation {

    @Test
    public void test() {
//        Assert.assertArrayEquals(new int[]{1, 3, 2}, nextPermutation(new int[]{1, 2, 3}));
//        Assert.assertArrayEquals(new int[]{3, 2, 1}, nextPermutation(new int[]{3, 1, 2}));
//        Assert.assertArrayEquals(new int[]{1, 2, 3}, nextPermutation(new int[]{3, 2, 1}));
//        Assert.assertArrayEquals(new int[]{1, 5, 1}, nextPermutation(new int[]{1, 1, 5}));
//        Assert.assertArrayEquals(new int[]{5, 1, 1}, nextPermutation(new int[]{1, 5, 1}));
        Assert.assertArrayEquals(new int[]{1, 1, 5}, nextPermutation(new int[]{5, 1, 1}));
    }

    /**
     * 条件：如果不存在下一个更大的排列,即当前序列已经是字典序中的最大序列，则将数字重新排列成最小的序列（即升序排列）。
     * 1.从右向左确定i位置，使nums[i]的右侧是逆序排列 即a[i] > a[i + 1] >...>end, 并且a[i - 1] < a[i] ;  i位置的右侧的序列是局部最大序列
     * 2.从a[i]位置向右遍历，找到a[j] 确保a[j] > a[i - 1] && a[j + 1] < a[i - 1] ，然后交互a[j] 和a[i - 1] ； 这样交互之后，i位置的右侧序列还是局部最大序列
     * 3.然后将a[i] ~ end 元素逆序 ；局部最大序列的下一个序列是最小序列正好是逆序
     *
     * @param nums
     */
    public int[] nextPermutation(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        if (nums.length == 2) {
            swop(nums, 0, 1);
            return nums;
        }
        int i = nums.length - 2;
        //此处的=是为了处理数组中重复数字的问题
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if(i >= 0) { //进到这里说明没有遍历完数组 当前序列不是最大序列
            //到这里表面nums[i] <= nums[i + 1] 进行第二步
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[j] > nums[i]) {
                    if(j == nums.length - 1 ) { //遍历到了最后一位 直接交互
                        swop(nums, j, i);
                    } else { //判断nums[j + 1] <= nums[i] 才交互为了不影响局部最大序列；此处的=是为了处理数组中重复数字的问题
                        if(nums[j + 1] <= nums[i]) {
                            swop(nums, j, i);
                        }
                    }
                }
            }
        } else {
            //当前序列是最大序列 什么都不需要做，直接将整个数组逆序即可  此时i = -1 下面的继续仍然适用
        }

        //第三步 i后面的元素逆序
        int l = i + 1, r = nums.length - 1;
        while(l < r) {
            swop(nums, l++, r--);
        }
        return nums;
    }

    private void swop(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}
