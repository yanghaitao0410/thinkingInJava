package com.yht.nowcode.basicstructure;

import com.yht.nowcode.stack_queue.TwoQueueStack;
import com.yht.nowcode.stack_queue.TwoStackQueue;
import org.junit.Test;

public class TestQueueStack {

    @Test
    public void testTwoQueueStack() {
        TwoQueueStack stack = new TwoQueueStack();
        stack.push(2);
        stack.push(1);
        stack.push(4);
        stack.pop();
        stack.push(3);
        System.out.println("peek : " + stack.peek());
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    @Test
    public void testTwoStackQueue() {
        TwoStackQueue queue = new TwoStackQueue();
        queue.push(2);
        queue.push(1);
        queue.push(4);
        queue.poll();
        queue.push(3);
        System.out.println("peek : " + queue.peek());
        while(!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

}
