package com.yht.nowcode.linkedlist;

import org.junit.Test;

public class LoopListTest {

    @Test
    public void testLoopList() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;

        Node firstLoopNode = LoopList.firstLoopNode(node1);
        System.out.println(firstLoopNode.value);


    }

}
