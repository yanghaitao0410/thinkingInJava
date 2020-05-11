package com.yht.nowcode.recurive;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static com.yht.nowcode.recursive.ReverseStack.getButtomNum;
import static com.yht.nowcode.recursive.ReverseStack.reverseStack;

/**
 * @author yht
 * @create 2020/5/11
 */
public class ReverseStackTest {
    Stack<Integer> stack = null;


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
