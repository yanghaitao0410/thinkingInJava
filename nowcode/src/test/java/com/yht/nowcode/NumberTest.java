package com.yht.nowcode;

import com.alibaba.fastjson.JSONObject;
import com.yht.nowcode.linkedlist.Node;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class NumberTest {

    @Test
    public void test1() {
        System.out.println(0 % 3);
    }

    @Test
    public void testSubList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.subList(0, 6).forEach(System.out::println);
    }

    @Test
    public void testReverseNumber() {
        System.out.println(reverse(1534236469));
    }

    public int reverse(int x) {
        if (x == 0)
            return 0;
        boolean abs = x > 0 ? true : false;
        int res = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            res = res * 10 + pop;
            //正数反转后若是负数，说明溢出  负数同理
            if ((abs && res < 0) || (!abs && res > 0)) {
                return 0;
            }
        }

        String str = 1 + "";
        return res;
    }

    @Test
    public void bigDecimalDemo() {
        System.out.println(BigDecimal.valueOf(8.1400001).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
    }

    @Test
    public void testRemove() {
//        removeElement(new int[]{3, 3}, 3);
        String str = "{\"t\":12,\"s\":\"12997\",\"room\":\"025\",\"bid\":\"1901090024\",\"dis\":0,\"card\":false,\"k\":1}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(jsonObject.getString("bid"));
    }

    public int removeElement(int[] nums, int val) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        //i表示正向遍历索引 j表示反向遍历索引
        int i = 0, j = nums.length - 1;

        while(i < j) {
            if(nums[i] == val) {
                if(nums[j] == val) {
                    while(j >= 0 && nums[j] == val) { //若j所在位置等于val，j跳到不等于val的位置
                        j--;
                    }
                    continue;  //跳出循环，再进行一次i 和j位置判断
                }
                swop(nums, i++, j--);
            }else {
                i++;
            }
        }
        //val为舍弃的值 若nums[i] 不是要舍弃的值 说明长度为i+1 否则长度就是i
        return nums[i] != val ? i + 1 : i;
    }

    private void swop(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }




}
