package com.yht.nowcode.array;

/**
 * dfs问题
 * 地上有一个 m 行和 n 列的方格。一个机器人从坐标 (0, 0) 的格子开始移动，
 * 每一次只能向左右上下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于 k 的格子。
 * <p>
 * 例如，当 k 为 18 时，机器人能够进入方格 (35,37)，因为 3+5+3+7=18。
 * 但是，它不能进入方格 (35,38)，因为 3+5+3+8=19。
 * 请问该机器人能够达到多少个格子？
 * 思路：一开始在 （0，0）位置，分别判断当前位置的上下左右是否满足条件，若满足 数目增加
 * 走过的路不再重复进
 */
public class MoveCount {

//    private int[][] digitArr;
    private static int movingCount;
//    private int[][] next = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    /**
     *
     * @param threshold 临界点
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @return 格子数
     */
    public int movingCount(int threshold, int rows, int cols) {
        movingCount = 0;
        int[][] digitArr = initDigitArr(rows, cols);
        boolean[][] marked = new boolean[rows][cols]; //是否遍历过标志 一开始都为false
        int[][] next = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        dfs(threshold, 0, 0, marked, digitArr, next);
        return movingCount;
    }

    /**
     *
     * @param threshold 临界值
     * @param curRow 当前行
     * @param curCol 当前列
     * @param marked 是否遍历过数组
     * @param digitArr 当前位置位数和数组
     * @param next 下次遍历位置数组
     */
    private void dfs(int threshold, int curRow, int curCol, boolean[][] marked, int[][] digitArr, int[][] next) {
        if(curRow < 0 || curCol < 0 || curRow >= digitArr.length
                || curCol >= digitArr[0].length || digitArr[curRow][curCol] > threshold || marked[curRow][curCol]) {
            return;
        }
        marked[curRow][curCol] = true;
        movingCount++;
        for(int[] nextArr : next) {
            dfs(threshold, curRow + nextArr[0], curCol + nextArr[1], marked, digitArr, next);
        }
    }

    /**
     * 初始化存储矩阵对应位置位数和的方法
     * @param rows
     * @param cols
     * @return
     */
    private int[][] initDigitArr(int rows, int cols) {
        int[][] digitArr = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            int iSum = getNumSum(i);
            for(int j = 0; j < cols; j++) {
                digitArr[i][j] = iSum + getNumSum(j);
            }
        }
        return digitArr;
    }

    /**
     * 返回数字的位数和
     * @param num
     * @return
     */
    private int getNumSum(int num) {
        int curValue = num, resultSum = 0;
        while (curValue > 0) {
            //将当前数的个位加到返回结果中
            resultSum += curValue % 10;
            //当前数个位舍去，然后整体右移一位
            curValue /= 10;
        }
        return resultSum;
    }

    public static void main(String[] args) {
        MoveCount moveCount = new MoveCount();
        System.out.println(moveCount.movingCount(15, 20, 20));
    }


}
