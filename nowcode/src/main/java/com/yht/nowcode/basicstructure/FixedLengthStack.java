package com.yht.nowcode.basicstructure;

/**
 * 一个长度确定的栈，构造方法传入长度后确定该栈的长度
 */
public class FixedLengthStack {
    private int index;
    private Integer[] array;
    public FixedLengthStack(int size) {
        array = new Integer[size];
        index = 0;
    }

    public int peek() {
        return array[index - 1];
    }

    public void push(int num) {
        if(index == array.length) {
            throw new RuntimeException("Stack is already full");
        }
        array[index++] = num;
    }

    public int pop() {
        if(index == 0) {
            throw new RuntimeException("Stack is empty");
        }
        return array[--index];
    }

    public boolean isEmpty() {
        return index == 0 ? true : false;
    }

}
