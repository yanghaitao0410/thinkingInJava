package com.yht.nowcode.linkedlist;

import org.junit.Before;
import org.junit.Test;

public class LoopListTest {
    public Node head1, head2, head;

    @Before
    public void before() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        //node4.next = node2;
        head = node1;
        head1 = node1;

        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node5.next = node6;
        node6.next = node4;
        head2 = node5;
    }

    @Test
    public void testLoopList1() {
        Node firstLoopNode = LoopList.firstLoopNode1(head);
        System.out.println(firstLoopNode.value);
    }

    @Test
    public void testLoopList2() {
        Node firstLoopNode = LoopList.firstLoopNode2(head);
        System.out.println(firstLoopNode.value);
    }

    @Test
    public void testGet2NoLoopFirstNode() {
        if(null != LoopList.get2NoLoopFirstNode1(head1, head2)) {
            System.out.println(LoopList.get2NoLoopFirstNode1(head1, head2));
        }else {
            System.out.println("null");
        }
    }


}
