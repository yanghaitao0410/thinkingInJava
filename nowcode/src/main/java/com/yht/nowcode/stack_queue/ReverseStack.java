package com.yht.nowcode.stack_queue;

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。
 */
public class ReverseStack {

    /**
     * 逆序栈
     * @param stack
     */
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




}
