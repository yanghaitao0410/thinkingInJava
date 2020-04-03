package com.yht.leetcode;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/3/1
 */
public class FindMedianSortedArrays {

    /**
     *为了解决这个问题，我们需要理解 “中位数的作用是什么”。在统计中，中位数被用来：
     * 将一个集合划分为两个长度相等的子集，其中一个子集中的元素总是大于另一个子集中的元素。
     * 首先，让我们在任一位置 ii 将 \text{A}A 划分成两个部分：
     *
     *           left_A             |        right_A
     *     A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
     *由于 A 中有 m 个元素， 所以我们有 m+1 种划分的方法（i=0∼m）。
     *
     * 我们知道：
     *
     *  len(left_A)=i,len(right_A) = m−i.
     *
     * 注意：当 i = 0时，left_A 为空集， 而当 i = m 时, right_A 为空集。
     *
     * 采用同样的方式，我们在任一位置 jj 将 \text{B}B 划分成两个部分：
     *
     *           left_B             |        right_B
     *     B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
     * 将 left_A 和 left_B 放入一个集合，并将 right_A 和 right_B 放入另一个集合。 再把这两个新的集合分别命名为 left_part 和 right_part：
     *
     *           left_part          |        right_part
     *     A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
     *     B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
     * 如果我们可以确认：
     *  条件1： len(left_part) = len(right_part)
     *  => 左边元素个数 = 右边元素个数 即 i + j = m - i + n - j （+1 奇数情况下） ：奇数情况下左边比右边多一个数
     *  => j = (m + n + 1) / 2 - i    => i增大，j会减小 ；i减小，j会增加
     *  注： 偶数情况下会有数组越界的问题 答案使用了 j = (m + n + 1) / 2 - i
     *
     *  所以 只要我们确定了i ，j的位置也就随之确定，通过公式计算得出的j位置一定满足 len(left_part) = len(right_part)
     *
     *  条件2：max(left_part) ≤ min(right_part)
     *  对应到A和B集合的对比 ：
     *      A[i - 1] <= B[j]
     *      B[j - 1] <= A[i]
     *
     *  那么，我们已经将 {A,B} 中的所有元素划分为相同长度的两个部分，且其中一部分中的元素总是大于另一部分中的元素。
     *  median = (max(left_part)+min(right_part)) / 2
     *
     *  通过二分法确定i的位置： 循环该判断过程
     *      设 imin=0，imax=m, 然后开始在 [imin,imax] 中定位i。
     *      i = (imin + imax) / 2
     *      j = (m + n + 1) / 2 - i
     *
     *  现在需要判断A[i - 1]、B[j]、B[j - 1]、A[i]的大小
     *
     *  情况1：A[i - 1] <= B[j] && B[j - 1] <= A[i] 找到了中位数
     *
     *  情况2：A[i - 1] > B[j] 说明A[i]太大了，因为是有序数组，所以i向左移动（减小）对应A[i] 也会随之减小 ，j会对应增大（通过公式） ，B[j−1]也就随之增大
     *  然后把[imin,imax] 区间缩小一半 即：imax=i−1 然后重新通过二分法确定i的位置
     *
     *  情况3：B[j−1] > A[i] 说明A[i]太小了，因为是有序数组，所以i向右移动（增大）对应A[i] 也会随之增大 ，j会对应减小（通过公式） ，B[j−1]也就随之减小
     *  然后把[imin,imax] 区间缩小一半 即： imin=i+1 然后重新通过二分法确定i的位置
     *
     *  最后得到了i、j的位置
     *  然后计算中位数
     *
     * ​
     *
     * @param A
     * @param B
     * @return
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length, n = B.length;
        if(n < m) { //调整顺序 让n >= m
            int[] tmps = A;
            A = B;
            B = tmps;

            int tmp = m;
            m = n;
            n = tmp;
        }

        int imin = 0, imax = m, halfLen;
        boolean oddNum = (m + n) % 2 == 1;
//        if(oddNum) {
//            halfLen = (m + n) / 2; //这种会出现数组越界问题 todo debug为什么
//        } else {
//            halfLen = (m + n + 1) / 2;
//        }
        halfLen = (m + n + 1) / 2;

        while(imin <= imax) {
            int i = (imin + imax) / 2;
            int j = halfLen - i;

            if(i < imax && A[i] < B[j - 1]) { //情况3
                imin = i + 1;
            }else if(i > imin && A[i - 1] > B[j]) { //情况2
                imax = i - 1;
            } else { //情况1 满足要求 整体的中位数 = （左边最大值 + 右边最小值） / 2
                int maxLeft;
                if(i == 0) { //中位数左边没有A集合，中位数直接是B[j - 1] 通过上面left_part right_part划分图得知
                    maxLeft = B[j - 1];
                } else if(j == 0) { //中位数左边没有B集合，中位数直接是A[i - 1] 通过上面left_part right_part划分图得知
                    maxLeft = A[i - 1];
                } else{
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }

                if(oddNum) { //总数为奇数个 中位数直接是左边最大值
                    return maxLeft * 1.0;
                }

                int minRight;
                if(i == m) {
                    minRight = B[j];
                } else if(j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(A[i], B[j]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

}
