package com.yht.nowcode.basicstructure;

import com.yht.nowcode.stack_queue.GetMinStack;
import com.yht.nowcode.stack_queue.GetMinStack2;
import org.junit.Test;

public class TestMinStack {

    @Test
    public void testMinStack1() {
        GetMinStack stack = new GetMinStack();
        stack.push(3);
        System.out.println(stack.getMin());
        stack.push(2);
        System.out.println(stack.getMin());
        stack.push(6);
        System.out.println(stack.getMin());
        stack.push(1);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
    }

    @Test
    public void testMinStack2() {
        GetMinStack2 stack = new GetMinStack2();
        stack.push(3);
        System.out.println(stack.getMin());
        stack.push(2);
        System.out.println(stack.getMin());
        stack.push(6);
        System.out.println(stack.getMin());
        stack.push(1);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
    }

}
