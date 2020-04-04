package com.yht.leetcode;

import org.junit.Test;

/**
 *
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 *  
 *
 * 示例:
 *
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 *
 * @author yht
 * @create 2020/4/2
 */
public class SwapPairs {

    @Test
    public void test() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        ListNode head = swapPairs(node1);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }


    /**
     * 遍历链表 准备2个指针 开始时分别指向1位置 2位置  然后1和2交互 1的下一个指向2的下一个位置
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode a = head;
        if(a == null) {
            return null;
        }
        ListNode b = a.next;
        if(b == null) {
            return a;
        }
        ListNode preNode = preHead; //一开始 a前面的节点是虚结点
        do {
            ListNode tmp = a;
            if(tmp == preHead.next) { //第一个节点 需要将preNode.next更新为b
                preHead.next = b;
            } else { //不是第一个节点 需要将a前一个节点的下一个节点更新为b
                preNode.next = b;
            }
            a.next = b.next;
            b.next = a;
            preNode = a; //a b已经交互，下一组要交互的a前面是当前的a

            a = a.next;
            if(a != null) {
                b = a.next;
            } else {
                b = null;
            }
        } while (b != null);

        return preHead.next;
    }

    /**
     * 官方递归法
     * @param head
     * @return
     */
    public ListNode swapPairs2(ListNode head) {

        // If the list has no node or has only one node left.
        if ((head == null) || (head.next == null)) {
            return head;
        }

        // Nodes to be swapped
        ListNode firstNode = head;
        ListNode secondNode = head.next;

        // Swapping firstNode交换之后变为第二个节点 这个节点的下一个节点通过递归得出
        firstNode.next  = swapPairs2(secondNode.next);
        secondNode.next = firstNode;

        // Now the head is the second node
        return secondNode;
    }

}
