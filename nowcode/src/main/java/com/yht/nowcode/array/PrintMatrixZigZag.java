package com.yht.nowcode.array;

/**
 * 之字形打印矩阵：
 *      给定一个矩阵matrix, 按照 "之" 字形的方式打印这个矩阵，例如：
 *           1   2   3   4
 *           5   6   7   8
 *           9  10  11  12
 *           打印的结果为：1，2，5，9，6，3，4，7，10，11，8，12
 *          【要求】额外的空间复杂度为O(1)
 *
 *          思路：还是不要考虑局部的下标变换，跳出来思考宏观调度
 *              准备两个指针一开始指向（0，0）位置（row1、col1、row2、col2），然后一个指针先向右移动，到边界后向下移动
 *              另一个指针同步向下移动，到边界后向右移动，每次打印移动后两点之间连线的数据就可以了
 *              打印的方向通过一个boolean变量控制，每打印一次就将该变量取反
 *
 */
public class PrintMatrixZigZag {

    public void printMatrixZigZag (int[][] matrix) {
        int rEnd = matrix.length - 1, cEnd = matrix[0].length - 1;
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        boolean side = true;
        while (row1 != rEnd + 1) {
            printLevel(matrix, row1, col1, row2, col2, side);
            row1 = col1 == cEnd ? row1 + 1 : row1;
            col1 = col1 == cEnd ? col1 : col1 + 1;
            col2 = row2 == rEnd ? col2 + 1 : col2;
            row2 = row2 == rEnd ? row2 : row2 + 1;
            side = !side;
        }
        System.out.println();
    }

    private void printLevel (int[][] matrix, int row1, int col1, int row2, int col2, boolean side) {
        if(side) { //从左下向右上打印
            while (row1 <= row2) {
                System.out.print(matrix[row1++][col1--] + " ");
            }
        }else { //从右上向左下打印
            while (row2 != row1 - 1) {
                System.out.print(matrix[row2--][col2++] + " ");
            }
        }
    }

}
