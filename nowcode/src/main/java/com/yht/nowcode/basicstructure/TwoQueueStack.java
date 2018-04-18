package com.yht.nowcode.basicstructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 栈结构
 *     借助队列实现
 *      实现：准备两个队列，一个data存压栈数据，help辅助队列
 *      压栈：直接data增加
 *      弹出：将data队列中的数据（除最后一个数）转移到help队列中
 *          然后用一个引用result接受data中最后一个数
 *      然后help和data的引用交换（为了循环利用）
 *      最后返回result
 */
public class TwoQueueStack {
    private Queue<Integer> data;
    private Queue<Integer> help;

    public TwoQueueStack() {
        data = new LinkedList<>();
        help = new LinkedList<>();
    }

    public void push(Integer num) {
        data.add(num);
    }

    public Integer pop() {
        if(data.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        while(data.size() > 1) {
            help.add(data.poll());
        }
        int result = data.poll();
        swop();
        return result;
    }

    /**
     * 返回数后交换help和data，以便下次弹出
     */
    private void swop() {
        Queue<Integer> tmp;
        tmp = data;
        data = help;
        help = tmp;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public Integer peek() {
        if(data.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        Integer result = pop();
        push(result);
        return result;
    }


}
