package com.yht.nowcode.graph.apply;

import java.util.Arrays;

/**
 * 岛问题
 * 一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右四个位置相连，
 * 如果有一片1相连，这个部分叫做一个岛，求一个矩阵中有多少个岛
 * 举例：
 * 0 0 1 0 1 0
 * 1 1 1 0 1 0
 * 1 0 0 1 0 0
 * 0 0 0 0 0 0
 * <p>
 * 这个矩阵中有三个岛
 *
 *
 * 进阶：如果矩阵很大，考率如何多cpu进行计算？
 *      假设将矩阵切成4块，分别在4台电脑上进行计算每一块岛的数量，最后合并成结果返回总体岛的数量
 *      思路：每块矩阵求出岛的数量和对应岛的感染中心，遍历相邻矩阵的边界，若边界两边都为1，判断两边所在的集合是否是同一个集合，
 *           若不是，总体岛的数量减一，然后将两个集合合并，然后继续遍历剩下的边界
 *           A              C
 *           1 1 1 1 1 1 | 1 1 1 1
 *           0 1 1 1 1 1 | 1 0 0 0
 *           0 0 0 0 0 0 | 1 0 0 1
 *           0 0 1 1 1 1 | 1 0 0 1
 *           0 0 0 0 0 1 | 0 0 1 1 D
 *                     B
 *              矩阵分为两块，计算之后左边有2个，右边有2个 感染中心分别为A, B, C, D
 *              遍历第一行边界 1, 1 判断A所在的集合和C所在的集合是否是相同集合：不是，岛的数量减一，然后合并AC
 *              遍历第二行边界，AC是相同集合，不变
 *              第3行不管
 *              遍历第4行，BC不是相同集合，岛数量减一，合并BC
 *              最后得到结果2返回
 */
public class Island {

    /**
     * 思路：从头开始遍历矩阵，若遍历到的位置是1，将其改为2， 岛的数量增1，然后将它上下左右节点位置的1改为2（递归）
     * 遍历位置为0或2跳过，遍历结束返回岛数量
     *
     * @param arr
     * @return
     */
    public static int getIslandCount(int[][] arr) {

        int [][]copyArr = Arrays.copyOf(arr, arr.length);
        int rowLength = arr.length;
        int colLength = arr[0].length;
        int islandCount = 0;
        for(int i = 0; i < rowLength; i++) {
            for(int j = 0; j < colLength; j++) {
                if(copyArr[i][j] == 1) {
                    islandCount++;
                    infection(copyArr, i, j);
                }
            }
        }
        return islandCount;
    }

    /**
     * 感染函数，将arr[row][col]位置的数修改为2， 然后递归感染它的上下左右
     * @param arr
     * @param row
     * @param col
     */
    private static void infection(int[][] arr, int row, int col) {
        if(row >= arr.length || row < 0 || col >= arr[0].length || col < 0 || arr[row][col] != 1) {
            return;
        }
        arr[row][col] = 2;
        infection(arr, row + 1, col);
        infection(arr, row - 1, col);
        infection(arr, row, col + 1);
        infection(arr, row, col - 1);
    }


}
