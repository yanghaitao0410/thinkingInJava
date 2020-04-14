package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * 在数组中找数问题
 */
public class SearchNum {

    @Test
    public void testSearchRotabe() {
        Assert.assertEquals(4, searchRotabe(new int[] {4, 5, 6, 7, 0, 1, 2}, 0));
        Assert.assertEquals(-1, searchRotabe(new int[] {4, 5, 6, 7, 0, 1, 2}, 3));
    }


    /**
     * 33. 搜索旋转排序数组
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     * 你可以假设数组中不存在重复的元素。
     *
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 示例 1:
     * 输入: nums = [4,5,6,7,0,1,2], target = 0
     * 输出: 4
     *
     * 示例 2:
     * 输入: nums = [4,5,6,7,0,1,2], target = 3
     * 输出: -1
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @author yht
     * @create 2020/4/8
     */
    public int searchRotabe(int[] nums, int target) {
        /*二分法找到中间的元素mid 因为当前数组是升序数组旋转之后得到的，所以mid两侧必有一边是完全升序的
        判断target是否在这个完全升序的范围内 不是的话去另一半查找
        */
        return binarySearchRotabe(nums, 0, nums.length - 1, target);

    }

    /**
     * 二分法在有旋转点的排序数组中找到目标
     * @param nums
     * @param l
     * @param r
     * @param target
     * @return
     */
    private int binarySearchRotabe(int[] nums, int l, int r, int target) {
        if(l == r) {
            if(nums[l] == target) {
                return l;
            } else {
                return -1;
            }
        }
        if(l > r) {
            return - 1;
        }

        int mid  = (l + r + 1) / 2;
        if(nums[mid] == target) {
            return mid;
        }
        if(nums[l] < nums[mid]) { //中点左边有序
            if(nums[l] <= target && nums[mid] >= target) { //target在左边有序的范围内
                return binarySearchRotabe(nums, l, mid - 1, target);
            } else { //不在左边范围内，去右边寻找
                return binarySearchRotabe(nums, mid + 1, r, target);
            }
        } else { //中点右边有序
            if(nums[mid] <= target && nums[r] >= target) {
                return binarySearchRotabe(nums, mid + 1, r, target);
            } else {
                return binarySearchRotabe(nums, l, mid - 1, target);
            }
        }
    }

    @Test
    public void testSearchRange() {
        Assert.assertArrayEquals(new int[]{3, 4}, searchRange2(new int []{5, 7, 7, 8, 8, 10}, 8));
        Assert.assertArrayEquals(new int[]{-1, -1}, searchRange2(new int []{5, 7, 7, 8, 8, 10}, 6));
        Assert.assertArrayEquals(new int[]{0, 0}, searchRange2(new int []{5, 7, 7, 8, 8, 10}, 5));
        Assert.assertArrayEquals(new int[]{0, 1}, searchRange2(new int []{2, 2}, 2));
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     * 你的算法时间复杂度必须是 O(log n) 级别。
     * 如果数组中不存在目标值，返回 [-1, -1]。
     *
     * 示例 1:
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: [3,4]
     *
     * 示例 2:
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: [-1,-1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        /*思路：
        *   先用二分法找到一个位置，然后在左右找到边界
        * 时间复杂度：最好情况O(log n) 最差情况O(n)
        * */
        int mid = binarySearch(nums, target, 0, nums.length - 1);
        if(mid == -1) {
            return new int[]{mid, mid};
        }
        int start = mid, end = mid;
        while ((start - 1 >=0 && nums[start - 1] == target) || (end + 1 < nums.length && nums[end + 1] == target)) {
            if(start - 1 >=0 && nums[start - 1] == target) {
                start--;
            }
            if(end + 1 < nums.length && nums[end + 1] == target) {
                end++;
            }
        }
        return new int[]{start, end};
    }

    /**
     * 二分法在排序数组中定位数据
     * @param nums
     * @param target
     * @param l
     * @param r
     * @return
     */
    public int binarySearch(int[] nums, int target, int l, int r) {
        if(l == r) {
            return nums[l] == target ? l : -1;
        }
        if(l > r) {
            return -1;
        }

        int mid = (l + r + 1) / 2;
        if(nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearch(nums, target, mid + 1, r);
        } else {
            return binarySearch(nums, target, l, mid - 1);
        }
    }

    /**
     * 1.首先用正常的二分查找定位到给定的元素，时间复杂度O(logN)
     * 2.在定位到指定元素的情况下，用O(logN)时间复杂度查找第一个/最后一个
     *
     * 什么情况说明找到了第一个元素呢？ i位置和target值相等
     * 当nums[i]大于nums[i-1]时。但还有一个特殊情况，nums[i]的下标为0时，这也很好理解，前面都没元素了，那当前元素肯定就是第一个了。
     *
     * 最后一个元素同理得出
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange2(int[] nums, int target) {
        if(nums == null)
            return new int[]{-1, -1};

        int firstIndex = extremeInsertionIndex(nums, target, true);
        int lastIndex = extremeInsertionIndex(nums, target, false);

        return new int[] {firstIndex, lastIndex};
    }

    /**
     * 查找第一个和最后一个元素
     * @param nums
     * @param target
     * @param isFindFirst true 查找第一个元素  false 查找最后一个元素
     * @return
     */
    private int extremeInsertionIndex(int[] nums, int target, boolean isFindFirst) {
        int begin = 0, end = nums.length - 1;

        while (begin <= end) {
            int mid = begin + (end - begin) / 2;

            //if和else if的逻辑跟正常的二分查找一样
            if(nums[mid] > target) {
                end = mid - 1;
            } else if(nums[mid] < target) {
                begin = mid + 1;

            } else { //找到目标值了，开始定位到第一个和最后一个位置
                if(isFindFirst) { //查找第一个元素
                    if(mid > 0 && nums[mid] == nums[mid - 1]) { //mid的前一个位置和target相等 end向前移动
                        end = mid - 1;
                    } else { //遍历到了第一个元素 或是前一个元素和当前元素不相等 直接返回mid
                        return mid;
                    }

                } else { //查找最后一个元素
                    if(mid < nums.length - 1 && nums[mid] == nums[mid + 1]) { //mid的后一个位置的值和target相等 begin向后移动
                        begin = mid + 1;
                    } else { //遍历导致最后一个元素 或时后一个元素和当前元素不相等 直接返回mid
                        return mid;
                    }
                }
            }
        }

        //运行到这里begin > end还没找到target元素 直接返回-1
        return -1;
    }



}
