package com.yht.nowcode.hash;

import org.junit.Test;

public class TestHash {

    @Test
    public void testRandomPool() {
        RandomPool pool = new RandomPool();

        pool.insert("a");
        pool.insert("b");
        pool.insert("c");
        pool.insert("d");
        pool.insert("e");

        pool.delete("c");

        int aCount = 0, bCount = 0, dCount = 0, eCount = 0;
        String tmp;
        for(int i = 0; i < 100000; i++) {
           tmp = pool.getRandom();
           if ("a".equals(tmp))
               aCount++;
           if ("b".equals(tmp))
               bCount++;
           if ("d".equals(tmp))
               dCount++;
           if ("e".equals(tmp))
               eCount++;
        }
        System.out.format("aCount: %d bCount: %d dCount: %d eCount: %d\n", aCount, bCount, dCount, eCount);

        /*运行5次结果：基本均匀返回
        *   aCount: 25050 bCount: 24811 dCount: 25224 eCount: 24915
        *   aCount: 24905 bCount: 25162 dCount: 25035 eCount: 24898
        *   aCount: 24884 bCount: 25134 dCount: 24995 eCount: 24987
        *   aCount: 25010 bCount: 24976 dCount: 24994 eCount: 25020
        *   aCount: 25150 bCount: 24978 dCount: 25117 eCount: 24755
        * */
    }
}
