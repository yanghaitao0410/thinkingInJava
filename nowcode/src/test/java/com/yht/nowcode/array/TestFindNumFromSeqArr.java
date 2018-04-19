package com.yht.nowcode.array;

import org.junit.Test;

public class TestFindNumFromSeqArr {

    @Test
    public void testFindNumFromSeqArr() {
        int[][] matrix = new int[][] {{0, 1, 2, 5}, {2, 3, 4, 7}, {4, 4, 4, 8}, {5, 7, 7, 9}};
        System.out.println(FindNumFromSeqArr.findNumFromSeqArr(matrix, 7));
        System.out.println(FindNumFromSeqArr.findNumFromSeqArr(matrix, 6));
    }
}
