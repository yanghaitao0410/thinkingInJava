package com.yht.leetcode.stack;

import java.util.Stack;

/**
 * @Desc 栈（stack）是很简单的一种数据结构，先进后出的逻辑顺序，符合某些问题的特点，比如说函数调用栈。
 * 单调栈实际上就是栈，只是利用了一些巧妙的逻辑，使得每次新元素入栈后，栈内的元素都保持有序（单调递增或单调递减）。
 * 听起来有点像堆（heap）？不是的，单调栈用途不太广泛，只处理一种典型的问题，叫做 Next Greater Element。
 * @Author water
 * @date 2020/9/4
 **/
public class NextGreaterElement {

    /**
     * 给定一个数组，返回一个等长的数组，对应索引存储着下一个更大元素，如果没有更大的元素，就存 -1
     * 例子：输入 [2,1,2,4,3]，返回数组 [4,2,4,-1,-1]。
     * 解释：第一个 2 后面比 2 大的数是 4; 1 后面比 1 大的数是 2；第二个 2 后面比 2 大的数是 4; 4 后面没有比 4 大的数，填 -1；3 后面没有比 3 大的数，填 -1。
     *
     * for 循环要从后往前扫描元素，因为我们借助的是栈的结构，倒着入栈，其实是正着出栈。
     * while 循环是把两个“高个”元素之间的元素排除，因为他们的存在没有意义，前面挡着个“更高”的元素，所以他们不可能被作为后续进来的元素的 Next Great Number 了。
     * @param nums
     * @return
     */
    public int[] nextGreaterElement(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = nums.length - 1; i >= 0; i--) {
            while(!stack.isEmpty() && stack.peek() <= nums[i]) { // 判定个子高矮
                stack.pop(); // 矮个起开，反正也被挡着了。。。
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek(); // 这个元素身后的第一个高个
            stack.push(nums[i]); // 进队，接受之后的身高判定吧！
        }

        return res;
    }

    /**
     * 给定一个数组 T = [73, 74, 75, 71, 69, 72, 76, 73]，这个数组存放的是近几天的天气气温（这里用的华氏度）。
     * 返回一个数组，计算：对于每一天，你还要至少等多少天才能等到一个更暖和的气温；如果等不到那一天，填 0 。
     *
     * 举例：给你 T = [73, 74, 75, 71, 69, 72, 76, 73]，你返回 [1, 1, 4, 2, 1, 1, 0, 0]。
     * 解释：第一天 73 华氏度，第二天 74 华氏度，比 73 大，所以对于第一天，只要等一天就能等到一个更暖和的气温。后面的同理。
     *
     *
     * @param nums
     * @return
     */
    public int[] dailyTemperatures(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for(int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }

    /**
     * 同样是 Next Greater Number，现在假设给你的数组是个环形的，如何处理？
     * 给定数组 [2,1,2,4,3]，返回数组 [4,2,4,-1,4]。拥有了环形属性，最后一个元素 3 绕了一圈后找到了比自己大的元素 4
     * 计算机的内存都是线性的，没有真正意义上的环形数组，但是我们可以模拟出环形数组的效果，一般是通过 % 运算符求模（余数），获得环形特效
     * <pre>{@code
     * int[] arr = {1,2,3,4,5};
     * int n = arr.length, index = 0;
     * while (true) {
     *     print(arr[index % n]);
     *     index++;
     * }}
     * </pre>
     *
     * 增加了环形属性后，问题的难点在于：这个 Next 的意义不仅仅是当前元素的右边了，有可能出现在当前元素的左边
     * 明确问题，问题就已经解决了一半了。我们可以考虑这样的思路：将原始数组“翻倍”，就是在后面再接一个原始数组，
     * 这样的话，按照之前“比身高”的流程，每个元素不仅可以比较自己右边的元素，而且也可以和左边的元素比较了
     *
     */
    public int[] nextGreaterElement2(int[] nums) {
        int[] res = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        // 假装这个数组长度翻倍了
        for(int i = nums.length * 2 - 1; i >= 0; i--) {
            while(!stack.isEmpty() && stack.peek() <= nums[i % nums.length]) { // 判定个子高矮
                stack.pop(); // 矮个起开，反正也被挡着了。。。
            }
            res[i % nums.length] = stack.isEmpty() ? -1 : stack.peek(); // 这个元素身后的第一个高个
            stack.push(nums[i % nums.length]); // 进队，接受之后的身高判定吧！
        }

        return res;
    }

}
