package com.yht.nowcode.heap;

import org.junit.Test;

public class MedianDataTest {

    @Test
    public void testGetMedianData() {
        MedianData medianData = new MedianData();
        medianData.add(3);
        medianData.add(1);
        medianData.add(6);
        medianData.add(9);
        medianData.add(2);
        medianData.add(4);
        medianData.add(0);
        medianData.add(10);
        medianData.add(-10);
        medianData.add(-100);

        System.out.println(medianData.getMedianDate());
    }
}
