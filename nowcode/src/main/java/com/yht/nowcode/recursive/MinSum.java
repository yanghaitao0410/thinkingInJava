package com.yht.nowcode.recursive;

/**
 * 给你一个二维数组，二维数组中每个数都是正数，要求从左上角走到右下角，每一步只能向右或向下。
 * 沿途的数字要累加起来。返回最小的路径和。
 */
public class MinSum {

    public static int getMinSum(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] minSumArr = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                minSumArr[i][j] = -1;
            }
        }
        return process(matrix, minSumArr,0, 0);
    }

    /**
     * 暴力递归版
     * 当前在matrix[i][j] 位置上
     * 假如走到最下行，接下来只能向右走
     * 假如走到最右列，接下开只能向下走
     * 其他情况，当前位置值 + 剩下位置最小路径和
     *
     * @param matrix
     * @param i
     * @param j
     * @return
     */
    public static int process(int[][] matrix, int[][] minSumArr, int i, int j) {
        int row = matrix.length;
        int col = matrix[0].length;
        int curValue = matrix[i][j];
        if (i == row - 1 && j == col - 1) { //走到最后，返回最后位置的值
            return curValue;
        }
        if (i == row - 1) { //走到了最下行，只能向右走
            return curValue + getValue(matrix, minSumArr, i, j + 1);
        }
        if (j == col - 1) {
            return curValue + getValue(matrix, minSumArr,i + 1, j);
        }
        return curValue + Math.min(getValue(matrix, minSumArr,i + 1, j), process(matrix, minSumArr, i, j + 1));
    }

    private static int getValue(int[][] matrix, int[][] minSumArr, int i, int j) {
        if (minSumArr[i][j] == -1) {
            minSumArr[i][j] = process(matrix, minSumArr, i, j);
        }
        return minSumArr[i][j];
    }

    public static void main(String[] args) {
        System.out.println(getMinSum(new int[][]{{3, 2, 1, 0}, {7, 5, 0, 1}, {3, 7, 6, 2}}));
    }
}
