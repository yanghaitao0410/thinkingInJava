package com.yht.nowcode.stack_queue;

import java.util.Stack;

/**
 * 用栈实现另一个栈的排序 使得该栈从顶到低为从小到大
 *
 * @author yht
 * @create 2020/5/13
 */
public class StackSortByStack {

    /**
     * 思路 从stack中得到最大的数，放到help中，让help最终排成栈顶最小 栈底最大
     * 然后依次压入stack中
     * @param stack
     */
    public void stackSortByStack(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            Integer cur = stack.pop();
            //stack弹出元素和help中最顶元素比较，若比help中元素大，则将help元素压入到stack
            //继续比较help中下一个元素，直到cur确定好在help中的位置
            while (!help.isEmpty() && cur > help.peek()) { //找到最大的数放到help最下面
                stack.push(help.pop());
            }
            help.push(cur);
        }

        while(!help.isEmpty()) {
            stack.push(help.pop());
        }
    }

}
