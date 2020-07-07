package com.yht.nowcode.stack_queue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 有一个整形数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向右边滑动一个位置
 * 如果数组长度为n，窗口大小为w，则一共产生n-w+1个窗口的最大值
 *
 * 请实现一个方法：传入整形数组arr和窗口大小w，返回一个长度为n-w+1的数组res，res[i]表示每一种窗口状态下的最大值
 * @author yht
 * @create 2020/6/1
 */
public class MaxWindow {

    public static void main(String[] args) {
        Arrays.stream(getMaxWindow(new int[]{4, 3, 5, 4, 3, 3, 6, 7}, 3)).forEach(item -> System.out.printf("%d ", item));
    }

    /**
     * 最优解版本
     * @param arr
     * @param w
     * @return
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if(arr == null || w < 1 || arr.length < w) {
            return null;
        }

        //队列中存储数组的下标，队列头为当前窗口最大值
        Deque<Integer> qmax = new LinkedList<>();
        int[] result = new int[arr.length - w + 1];
        int resultIndex = 0;

        //从arr[0]开始遍历数组
        for(int i = 0; i < arr.length; i++) {

            //循环判断队尾元素是不是大于当前遍历元素，小于就出队（队尾元素小于当前元素，不可能是当前窗口最大值)
            while (!qmax.isEmpty() && arr[qmax.peekLast()] < arr[i]) {
                qmax.pollLast();
            }
            //此时队列中没有比当前元素更小的数了，当前元素入队
            qmax.addLast(i);
            //判断此时队头元素是否过期
            if(qmax.peekFirst() <= i - w) {
                qmax.pollFirst();
            }

            //当前元素位置大于等于窗口-1 才有窗口最大值
            if(i >= w - 1) {
                //当前窗口的最大值就是队头元素
                result[resultIndex++] = arr[qmax.peekFirst()];
            }
        }
        return result;
    }
}
