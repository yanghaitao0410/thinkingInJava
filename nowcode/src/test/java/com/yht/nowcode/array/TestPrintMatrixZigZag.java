package com.yht.nowcode.array;

import org.junit.Test;

import java.util.Arrays;

public class TestPrintMatrixZigZag {

    @Test
    public void testPrintMatrixZigZag () {
        int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8},{9, 10, 11, 12}};
        PrintMatrixZigZag printMatrixZigZag = new PrintMatrixZigZag();
        printMatrixZigZag.printMatrixZigZag(matrix);
    }

    @Test
    public void test() {
//        String[] arr = ",sdf,".split(",");
//
//        for(String string : arr) {
//            System.out.println("string:" + string);
//        }

        System.out.println("2020-02-23 23:23:12".replaceAll(" ", "_").replaceAll(":", "_"));
    }
}
