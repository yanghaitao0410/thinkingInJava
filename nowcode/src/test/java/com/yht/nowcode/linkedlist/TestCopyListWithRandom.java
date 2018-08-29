package com.yht.nowcode.linkedlist;

import org.junit.Before;
import org.junit.Test;

public class TestCopyListWithRandom {
    RandomNode head = null;

    @Before
    public void before() {
        RandomNode node = new RandomNode(1);
        RandomNode node2 = new RandomNode(2);
        RandomNode node3 = new RandomNode(3);
        node.next = node2;
        node2.next = node3;
        node.random = node3;
        node2.random = node;
        head = node;
    }

    @Test
    public void testCopyListWithRandom1() {

        RandomNode newNode = CopyListWithRandom.copyRandomList1(head);
        RandomNode curNode = newNode;
        while(curNode != null) {
            System.out.print("curNode.nodeNumber : " + curNode.value + " ");

            System.out.print(curNode.next != null ?
                    "curNode.next.nodeNumber : " + curNode.next.value + " " : "curNode.next.nodeNumber : null ");
            System.out.print(curNode.random != null ?
                    "curNode.random.nodeNumber : " + curNode.random.value + " " : "curNode.random.nodeNumber : null ");

            curNode = curNode.next;
            System.out.println();
        }

        /*
        输出结果：
            curNode.nodeNumber : 1 curNode.next.nodeNumber : 2 curNode.random.nodeNumber : 3
            curNode.nodeNumber : 2 curNode.next.nodeNumber : 3 curNode.random.nodeNumber : 1
            curNode.nodeNumber : 3 curNode.next.nodeNumber : null curNode.random.nodeNumber : null
         */

    }

    @Test
    public void testCopyListWithRandom2() {

        RandomNode newNode = CopyListWithRandom.copyRandomList2(head);
        RandomNode curNode = newNode;
        while(curNode != null) {
            System.out.print("curNode.nodeNumber : " + curNode.value + " ");

            System.out.print(curNode.next != null ?
                    "curNode.next.nodeNumber : " + curNode.next.value + " " : "curNode.next.nodeNumber : null ");
            System.out.print(curNode.random != null ?
                    "curNode.random.nodeNumber : " + curNode.random.value + " " : "curNode.random.nodeNumber : null ");

            curNode = curNode.next;
            System.out.println();
        }

        /*
        输出结果：
            curNode.nodeNumber : 1 curNode.next.nodeNumber : 2 curNode.random.nodeNumber : 3
            curNode.nodeNumber : 2 curNode.next.nodeNumber : 3 curNode.random.nodeNumber : 1
            curNode.nodeNumber : 3 curNode.next.nodeNumber : null curNode.random.nodeNumber : null
         */

    }
}
