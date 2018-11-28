package com.yht.nowcode.recursive;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。
 */
public class ReverseStack {

    Stack<Integer> stack = null;

    public static void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty())
            return;
        //得到栈底元素
        int num = getButtomNum(stack);
        //逆序栈中剩下的元素
        reverseStack(stack);
        //将栈底元素压入栈顶
        stack.push(num);
    }

    /**
     * 将栈底元素弹出返回
     *
     * @param stack
     */
    public static int getButtomNum(Stack<Integer> stack) {
        Integer result = null;
        int num = stack.pop();
        if (stack.isEmpty()) {
            result = num;
        } else {
            result = getButtomNum(stack);
            stack.push(num);
        }
        return result;
    }

    @Before
    public void startUp() {
        stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
    }

    @Test
    public void testGetButtomNum() {
        Assert.assertEquals(1, getButtomNum(stack));
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    @Test
    public void testReverseStack() {
        reverseStack(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }


}
