package com.yht.nowcode.array;

import org.junit.Test;

public class TestPrintMatrixZigZag {

    @Test
    public void testPrintMatrixZigZag () {
        int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8},{9, 10, 11, 12}};
        PrintMatrixZigZag printMatrixZigZag = new PrintMatrixZigZag();
        printMatrixZigZag.printMatrixZigZag(matrix);
    }
}
