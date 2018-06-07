package com.yht.nowcode.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 随时找到数据流的中位数
 * 【题目】
 * 有一个源源不断吐出整数的数据流，假设你有足够的空间来保存吐出的数。
 * 请设计一个名为MedianHolder的结构，MadianHolder可以随时取得之前吐出所有数的中位数
 * 【要求】
 * 1.如果MedianHolder已经保存了吐出的N个数，那么任意时刻将一个新数加入到MedianHolder的过程，
 * 其时间复杂度是O(logN)
 * 2.取得已经吐出的N个数整体中位数的过程，时间复杂度是O(1)
 * <p>
 * 思路：准备一个大根堆（完全二叉树，根在其所在子树中值最大）和一个小根堆（完全二叉树，根在其所在子树中值最小）
 * 接收数据，第一个数据直接进大根堆，
 * 从第二个数据开始与大根堆堆顶元素进行比较，若比大根堆堆顶元素小：进大根堆 否则进小根堆
 * 每进入完成一个数后比较大根堆和小根堆的数量，若数量差值大于1，多数据的堆弹出堆顶元素进另一个堆
 * <p>
 * 最后的表现形式：接收数据按大小排序（展示形式，并未真正进行排序），前一半在大根堆，后一半在小根堆
 * 中位数在两个堆的堆顶产生（通过总体数量产生）
 */
public class MedianData {
    private PriorityQueue<Integer> bigHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2) {
                return 1;
            } else if (o1 > o2) {
                return -1;
            } else {
                return 0;
            }
        }
    });

    private PriorityQueue<Integer> smallHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2) {
                return -1;
            } else if (o1 > o2) {
                return 1;
            } else {
                return 0;
            }
        }
    });

    /**
     * 接收数据
     * @param num
     */
    public void add(Integer num) {
        if(bigHeap.size() == 0) {
            bigHeap.add(num);
        }else {
            if (bigHeap.peek() > num) {
                bigHeap.add(num);
            } else {
                smallHeap.add(num);
            }
        }
        balanceHeap();
    }

    /**
     * 平衡大根堆和小根堆，使得两个堆数量最多差一个
     */
    private void balanceHeap() {
        int bigSize = bigHeap.size();
        int smallSize = smallHeap.size();
        if(bigSize > smallSize && bigSize - smallSize > 1) {
            smallHeap.add(bigHeap.poll());
        }else if(smallSize > bigSize && smallSize - bigSize > 1) {
            bigHeap.add(smallHeap.poll());
        }
    }

    /**
     * 获取当前接收数据中的中位数
     * @return
     */
    public Integer getMedianDate() {
        int bigSize = bigHeap.size(), smallSize = smallHeap.size();
        if((bigSize + bigSize) % 2 == 1) { //奇数个
            if(bigSize > smallSize) {
                return bigHeap.peek();
            } else {
                return smallHeap.peek();
            }
        } else{
            return (bigHeap.peek() + smallHeap.peek()) / 2;
        }
    }

}
