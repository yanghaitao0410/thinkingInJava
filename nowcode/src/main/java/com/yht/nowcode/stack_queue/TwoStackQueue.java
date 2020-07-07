package com.yht.nowcode.stack_queue;

import java.util.Stack;

/**
 * <p>
 * 队列结构
 *    借助两个栈实现
 *      stackPush栈负责入队操作，stackPop栈负责出队操作
 *      数压入到stackPush中，再弹出压入到stackPop栈中就和一开始的顺序一致了
 *      这样就实现了队列的先进先出
 *
 *    但是从stackPush弹出压入stackPop有两个重要的原则：
 *      1：只有当stackPop栈为空的时候才能进行压入操作
 *      2：压入操作需要将stackPush栈中元素全部弹出压到stackPop栈中，不能有剩余
 *
 *    压入的时机：
 *      push、pop、peek、或是离线脚本中都可以
 *
 * </p>
 */
public class TwoStackQueue {
    private Stack<Integer> stackPush;
    private Stack<Integer> stackPop;

    public TwoStackQueue() {
        stackPush = new Stack<>();
        stackPop = new Stack<>();
    }

    public void push(Integer num) {
        stackPush.push(num);
        shiftData();
    }

    public Integer poll() {
        if(stackPush.isEmpty() && stackPop.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        shiftData();
        return stackPop.pop();
    }

    public Integer peek() {
        if(stackPush.isEmpty() && stackPop.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        shiftData();
        return stackPop.peek();
    }

    public boolean isEmpty() {
        return stackPop.isEmpty() && stackPush.isEmpty();
    }

    /**
     * 将数据从stackPush栈中移动到stackPop栈中
     */
    private void shiftData() {
        if(stackPop.isEmpty()) { //原则1
            while(!stackPush.isEmpty()) { //原则2
                stackPop.push(stackPush.pop());
            }
        }
    }

}
