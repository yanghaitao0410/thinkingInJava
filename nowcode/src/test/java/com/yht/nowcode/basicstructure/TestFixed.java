package com.yht.nowcode.basicstructure;

import org.junit.Test;

public class TestFixed {

    @Test
    public void testStack() {
        FixedLengthStack stack = new FixedLengthStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        //stack.push(1);

        System.out.println("peek: " + stack.peek());

        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        //stack.pop();

    }

    @Test
    public void testQueue() {
        FixedLengthQueue queue = new FixedLengthQueue(3);
        queue.push(1);
        queue.push(2);
        queue.push(3);
        //System.out.println(queue);
        queue.pop();
        queue.pop();
        queue.push(4);
        queue.push(5);
        queue.pop();
        queue.push(6);

        //System.out.println(queue);
        while(!queue.isEmpty()) {
            System.out.println(queue.pop());
        }
    }
}
