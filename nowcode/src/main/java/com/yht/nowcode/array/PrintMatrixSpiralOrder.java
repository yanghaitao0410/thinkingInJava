package com.yht.nowcode.array;

/**
 * 转圈打印矩阵
 *      给定一个整形矩阵matrix, 请按照转圈的方式打印它
 *      例如：
 *           1   2   3   4
 *           5   6   7   8
 *           9  10  11  12
 *          13  14  15  16
 *      打印结果为：1，2，3，4，8，12，16，15，14，13，9，5，6，7，11，10
 *
 *      【要求】 ：额外空间复杂度为O(1)
 *
 *      思路：需要锻炼宏观思考的能力，从row和col转向的细节中跳出来，
 *          首先确定矩阵的左上点坐标和右下点坐标，然后打印这一圈的数字，
 *          打印之后将左上点坐标++， 右下点坐标--，循环打印每一圈
 *          直到某一坐标相等
 */
public class PrintMatrixSpiralOrder {
    public void printMatrixSpiralOrder (int[][] matrix) {
        int row1 = 0,
                col1 = 0,
                row2 = matrix.length - 1,
                col2 = matrix[0].length - 1;
        while (row1 <= row2 && col1 <= col2) {
            printOneRount(matrix, row1++, col1++, row2--, col2--);
        }
    }

    private void printOneRount (int[][] matrix, int row1, int col1, int row2, int col2) {
        if (row1 == row2) { //数据只有一列
            int index = col1;
            while (index <= col2) {
                System.out.print(matrix[row1][index++] + " ");
            }
        }else if(col1 == col2) { //数据只有一行
            int index = row1;
            while (index <= row2) {
                System.out.print(matrix[index++][col1] + " ");
            }
        }else {
            int cIndex = col1;
            int rIndex = row1;
            while (cIndex < col2) {
                System.out.print(matrix[row1][cIndex++] + " ");
            }
            while (rIndex < row2) {
                System.out.print(matrix[rIndex++][col2] + " ");
            }
            while (col1 < col2) {
                System.out.print(matrix[row2][col2--] + " ");
            }
            while (row1 < row2) {
                System.out.print(matrix[row2--][col1] + " ");
            }
        }

    }

}
