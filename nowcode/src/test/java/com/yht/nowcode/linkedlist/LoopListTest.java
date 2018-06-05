package com.yht.nowcode.linkedlist;

import org.junit.Test;

public class LoopListTest {
    public Node head1, head2, head;


    /**
     * 测试返回链表的第一个入环点
     */
    @Test
    public void testLoopList1() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node3;
        head = node1;
        Node firstLoopNode = LoopList.firstLoopNode1(head);
        System.out.println(firstLoopNode.value);
    }

    /**
     * 测试返回链表的第一个入环点 2
     */
    @Test
    public void testLoopList2() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node3;
        head = node1;
        Node firstLoopNode = LoopList.firstLoopNode2(head);
        System.out.println(firstLoopNode.value);
    }

    /**
     * 测试两个无环链表相交问题
     */
    @Test
    public void testGet2NoLoopFirstNode() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        node5.next = node6;
        node6.next = node3;

        head1 = node1;
        head2 = node5;

        if(null != LoopList.get2NoLoopFirstNode1(head1, head2, null)) {
            System.out.println(LoopList.get2NoLoopFirstNode1(head1, head2, null));
        }else {
            System.out.println("null");
        }
    }


    /**
     * 测试第一个相交点在共同环的前面
     */
    @Test
    public void testGet2NodeFirstLoop() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node3;

        node7.next = node2;

        head1 = node1;
        head2 = node7;

        System.out.println(LoopList.get2NodeFirstLoop(head1, head2));
    }

    /**
     * 测试两个链表的入环点在环上
     */
    @Test
    public void testGet2NodeFirstLoop2() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2;

        node6.next = node5;

        head1 = node1;
        head2 = node6;
        System.out.println(LoopList.get2NodeFirstLoop(head1, head2));
    }

    /**
     * 测试两个有环不相交链表
     */
    @Test
    public void testGet2NodeFirstLoop3() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2;

        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;
        node10.next = node8;

        head1 = node1;
        head2 = node6;

        System.out.println(LoopList.get2NodeFirstLoop(head1, head2));
    }


}
