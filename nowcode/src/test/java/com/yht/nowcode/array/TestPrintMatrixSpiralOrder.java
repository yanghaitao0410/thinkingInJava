package com.yht.nowcode.array;

import org.junit.Test;

public class TestPrintMatrixSpiralOrder {

    @Test
    public void testPrintOrder() {
        PrintMatrixSpiralOrder printMatrixSpiralOrder = new PrintMatrixSpiralOrder();
        int[][] matrix = new int[4][];
        int index = 0;

        for(int i = 0; i < 4; i++) {
            matrix[i] = new int[4];
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = index++;
            }
        }
        printMatrixSpiralOrder.printMatrixSpiralOrder(matrix);
    }
}
