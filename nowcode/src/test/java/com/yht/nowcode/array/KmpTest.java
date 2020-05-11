package com.yht.nowcode.array;

import org.junit.Test;

import java.util.stream.Stream;

import static com.yht.nowcode.array.Kmp.getIndexOf;
import static com.yht.nowcode.array.Kmp.getSubStringArr2;

/**
 * @author yht
 * @create 2020/5/11
 */
public class KmpTest {

    @Test
    public void testGetSubStringArr () {
        Stream.of(getSubStringArr2("ababcababtk")).forEach(x -> System.out.printf("%d ", x));
    }

    @Test
    public void testKmp() {
        System.out.println(getIndexOf("abeabcdd", "abc"));
    }

}
