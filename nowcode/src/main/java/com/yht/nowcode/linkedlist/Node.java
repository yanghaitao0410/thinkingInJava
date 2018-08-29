package com.yht.nowcode.linkedlist;

public class Node {
    public int value;
    public Node next;
    public Node(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Node{" +
                "nodeNumber=" + value +
                '}';
    }
}
