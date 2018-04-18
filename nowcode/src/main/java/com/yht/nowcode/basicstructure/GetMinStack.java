package com.yht.nowcode.basicstructure;

import java.util.Stack;

/**
 * 实现一个特殊的栈，再实现栈的基本功能的基础上，再实现返回栈中最小元素的操作
 * 要求：pop()、push、getMin()操作的时间复杂度都为O(1)
 *      设计的栈类型可以使用现成的栈结构
 *
 * 实现1：min栈和data栈同步压入弹出，
 *          压栈操作：若当前数小于等于min栈顶元素，压入，否则重复压入栈定元素
 *          出栈操作：同步出栈
 *          返回最小值：min栈顶元素即当前数据的最小值
 */
public class GetMinStack {
    private Stack<Integer> data;
    private Stack<Integer> min;

    public GetMinStack() {
        data = new Stack<>();
        min = new Stack<>();
    }

    public void push(Integer num) {
        data.push(num);
        if(min.isEmpty()) {
            min.push(num);
        }else {
            min.push(Math.min(min.peek(), num));
        }
    }

    public Integer pop() {
        if(data.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        min.pop();
        return data.pop();
    }

    public Integer getMin() {
        if(min.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return min.peek();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }



}
