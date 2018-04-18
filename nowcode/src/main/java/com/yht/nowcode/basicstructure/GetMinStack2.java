package com.yht.nowcode.basicstructure;

import java.util.Stack;

/**
 * 优化版本：
 *      压入操作：当前数和min栈顶元素比较，小于等于才压入     大于不压入
 *      弹出操作：data正常弹，和min栈顶元素比较，相等min栈才弹，否则不弹
 */
public class GetMinStack2 {
    private Stack<Integer> data;
    private Stack<Integer> min;

    public GetMinStack2() {
        data = new Stack<>();
        min = new Stack<>();
    }

    public void push(Integer num) {
        data.push(num);
        if(min.isEmpty()) {
            min.push(num);
        }else if(min.peek() >= num) {
            min.push(num);
        }
    }

    public Integer pop() {
        if(data.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        int result = data.pop();
        if(result == min.peek()) {
            min.pop();
        }
        return result;
    }

    public Integer getMin() {
        return min.peek();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

}