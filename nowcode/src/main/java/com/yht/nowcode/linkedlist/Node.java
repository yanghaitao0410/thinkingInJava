package com.yht.nowcode.linkedlist;

import java.util.ArrayList;
import java.util.List;

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

    public static Node addTwoNumbers(Node l1, Node l2) {
        long sum = 0, scale = 1;
        while(l1 != null || l2 != null){
            if(l1 != null) {
                sum = sum + l1.value * scale;
                l1 = l1.next;
            }
            if(l2 != null) {
                sum = sum + l2.value * scale;
                l2 = l2.next;
            }
            scale *= 10;
        }
        scale = 10;
        Node resultRoot = new Node((int)(sum % scale));
        sum = sum / 10;
        Node curNode = resultRoot;
        while(sum > 0) {
            Node listNode = new Node((int)(sum % scale));
            sum = sum / 10;
            curNode.next = listNode;
            curNode = listNode;
        }
        return resultRoot;
    }

    public static void main(String[] args) {
        Node node1 = new Node(9);
        Node node2 = new Node(1);
        Node node2_1 = new Node(9);
        Node node2_2 = new Node(9);
        Node node2_3 = new Node(9);
        Node node2_4 = new Node(9);
        Node node2_5 = new Node(9);
        Node node2_6 = new Node(9);
        Node node2_7 = new Node(9);
        Node node2_8 = new Node(9);
        Node node2_9 = new Node(9);
        node2.next = node2_1;
        node2_1.next = node2_2;
        node2_2.next = node2_3;
        node2_3.next = node2_4;
        node2_4.next = node2_5;
        node2_5.next = node2_6;
        node2_6.next = node2_7;
        node2_7.next = node2_8;
        node2_8.next = node2_9;

        Node resultRoot = addTwoNumbers(node1, node2);
        while (resultRoot != null) {
            System.out.printf("%d -> ", resultRoot.value);
            resultRoot = resultRoot.next;
        }

    }
}
