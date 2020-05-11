package com.yht.nowcode.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入：
 * 参数1，正数数组costs
 * 参数2，正数数组profits
 * 参数3，正数k
 * 参数4，正数m
 *
 * costs[i] 表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱（利润）
 * k表示不能并行，只能串行最多做k个项目
 * m表示初始资金
 *
 * 说明：每做完一个项目，马上获得收益，可以支持你去做下一个项目
 *
 * 输出：你最后获得的最大钱数
 */
public class MaxProfit {

    /**
     * 思路：考虑使用贪心策略，
     *  step1 :先筛选出在m初始资金以内的项目，
     *  step2：从这些项目中选择利润最大的，
     *  step3:基本资金+=利润
     *  step4:重复k次之前的步骤
     */

    /**
     * 具体实现：
     *  准备一个小根堆（以项目花费排序）
     *  准备一个大根堆（以利润排序）
     *  一开始将项目放入小根堆
     *  弹出数据和m比较，若比m小，说明可以做该项目，将其放入大根堆
     *  直到小根堆中没有比m小的数据
     *  然后从大根堆中弹出利润最高的项目
     *  m = m + profit
     *  重复上面的步骤
     */

    private static PriorityQueue<Project> minProjects = new PriorityQueue<>(new Comparator<Project>() {
        @Override
        public int compare(Project o1, Project o2) {
           if(o1.costs < o2.costs) {
               return -1;
           } else if(o1.costs > o2.costs) {
               return  1;
           }
           return 0;
        }
    });

    private static PriorityQueue<Project> maxProjects = new PriorityQueue<>(new Comparator<Project>() {
        @Override
        public int compare(Project o1, Project o2) {
            if(o1.profit > o2.profit) {
                return -1;
            } else if(o1.profit < o2.profit) {
                return 1;
            }
            return 0;
        }
    });


    public static Integer maxProfit(Integer[] costs, Integer[] profit, int k, int m) {
        int count = costs.length, index = 0;
        int maxProfit = 0;
        if(costs.length != profit.length || k < 0 || m < 0)
            throw new RuntimeException("param error");

        for(int i = 0; i < count; i++) {
            minProjects.add(new Project(costs[i], profit[i]));
        }

        while(index < k) {
            while (!minProjects.isEmpty() && minProjects.peek().costs < m) {
                maxProjects.add(minProjects.poll());
            }
            if(maxProjects.isEmpty())
                continue;
            int curProfit = maxProjects.poll().profit;
            maxProfit += curProfit;
            m += curProfit;
            index++;
        }

        return maxProfit;
    }


    public static void main(String[] args) {
        Integer[] costs = {3, 5, 1, 8};
        Integer[] profit = {5, 6, 3, 9};
        System.out.println(maxProfit(costs, profit, 2, 3));
    }





}
