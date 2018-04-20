package com.yht.nowcode.linkedlist;

import org.junit.Before;
import org.junit.Test;

public class PationTest {
    Node node;

    @Before
    public void beforeTest() {
        node = new Node(9);
        Node node2 = new Node(0);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        Node node5 = new Node(1);
        Node node6 = new Node(3);
        Node node7 = new Node(3);
        node.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
    }

    @Test
    public void testPation1() {
        Node head = PationList.pationList1(node, 3);
        while(head != null) {
            System.out.print(head.value + "->");
            head = head.next;
        }
    }
}
