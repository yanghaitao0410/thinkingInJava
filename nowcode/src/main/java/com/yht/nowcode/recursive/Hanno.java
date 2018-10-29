package com.yht.nowcode.recursive;

import org.junit.Test;

/**
 * 汉诺塔问题
 * 有 from  to  help 3个杆，
 * 一开始from上有N个有序数，借助help，将数从from移动到to
 * 要求数的顺序不变，移动过程中小数在大数上面，一次只可以移动一个数
 */
public class Hanno {

    /**
     * 递归思路：
     *  要想移动N规模的数从from到to，
     *      1.需要将N-1规模的数从from移动到help,此时辅助杆变为to
     *      2.将单个数N从from移动到to
     *      3.将N-1规模的数从help移动到to 此时辅助杆变为from
     * @param N 表示 需要移动到数为 1 ~ N
     * @param from 开始杆
     * @param to 结束杆
     * @param help 辅助杆
     */
    public void moveHanno(int N, String from, String to, String help) {
        if(N == 1) {
            System.out.printf("move 1 from %s to %s\n", from, to);
        }else {
            moveHanno(N-1, from, help, to);
            System.out.printf("move %s from %s to %s\n", N, from, to);
            moveHanno(N-1, help, to, from);
        }
    }

    @Test
    public void test(){
        moveHanno(10, "左", "右", "中");
    }
}
