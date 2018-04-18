package com.yht.nowcode.basicstructure;

import java.util.Stack;

/**
 * 队列结构 </br>
 *    借助两个栈实现 </br>
 *      stackPush栈负责加数，stackPop栈负责出数 </br>
 *      将stackPush中的数倒入stackPop栈中，然后再弹出就实现了队列的先进先出  </br>
 *    但是倒入有两个重要的原则：</br>
 *      1：只有当stackPop栈为空的时候才能进行倒入操作</br>
 *      2：倒入操作将stackPush栈中元素全部倒入stackPop栈中，不能有剩余</br>
 *</br>
 *    倒入的时机再哪都可以：</br>
 *      push、pop、或是离线脚本中都可以</br>
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
