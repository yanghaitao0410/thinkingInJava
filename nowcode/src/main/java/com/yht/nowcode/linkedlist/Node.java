package com.yht.nowcode.linkedlist;

public class Node {
    public int value;
    public Node next;
    public Node(int value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
