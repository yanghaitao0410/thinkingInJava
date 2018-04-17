package com.yht.nowcode.basicstructure;

/**
 * 长度固定的队列,循环使用空间
 * 将start和end解耦，只和size有关系
 */
public class FixedLengthQueue {
    private int[] arr;
    private int start; //取数位置 （队头）
    private int end; //放数位置（队尾）
    private int size;  //已经存放数据个数
    public FixedLengthQueue(int len) {
        arr = new int[len];
        start = 0;
        end = 0;
        size = 0;
    }
    public int peek() {
        if(size == 0) {
            throw new RuntimeException("Queue is empty");
        }
        return arr[start];
    }

    public void push(int num) {
        if(size == arr.length) {
            throw new RuntimeException("Queue is full");
        }
        size++;
        arr[end] = num;
        end = nextIndex(end);
    }

    private int nextIndex(int index) {
        return index == arr.length - 1 ? 0 : index + 1;
    }

    public int pop() {
        if(size == 0) {
            throw new RuntimeException("Queue is empty");
        }
        int result = arr[start];
        start = nextIndex(start);
        size--;
        return result;
    }

    public boolean isEmpty() {
        return  size > 0 ? false : true;
    }
}
