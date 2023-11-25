package com.yht.algo.linklist;

/**
 * https://leetcode.cn/problems/reverse-nodes-in-k-group/
 * k个节点的组内逆序调整
 *
 * 给定一个单链表的头节点head，和一个正数k
 * 实现k个节点的小组内部逆序，如果最后一组不够k个就不调整
 *
 * 例子：
 * 调整前：1 -> 2 -> 3 ->4 -> 5 -> 6 ->7 -> 8 ,k = 3
 * 调整后：3 -> 2 -> 1 ->6 -> 5 -> 4 ->7 -> 8
 */
public class LinkListKGroupNodeReverse {

    public Node linkListKGroupNodeReverse(Node head, int k) {
        /*
            思路 ：
                设计一个能够返回从head开始，第k个节点的函数
                设计一个给定开始节点到结束节点，逆序从开始到结束位置链表的函数
                然后设计整体流程
         */
        Node start = head;
        Node end = getIndexNode(start, k);
        if (end == null) {
            return head;
        }
        //逆序后 end变成头 start变成尾 需要返回第一次逆序之后的头，然后用第一次的尾接收后续逆序后的头
        Node result = end;
        reverseNode(start, end);
        //逆序后的尾
        Node lastEnd = start;

        while(lastEnd.next != null) {
            start = lastEnd.next;
            end = getIndexNode(start, k);
            if (end == null) {
                return result;
            }
            reverseNode(start, end);
            //使用上一组的尾接收当前组逆序后的头 end
            lastEnd.next = end;
            //更新上一组的尾为当前组的尾，为下组逆序做准备
            lastEnd = start;
        }

        return result;
    }

    private void reverseNode(Node start, Node end) {
        if (start == null || end == null) {
            return;
        }
        end = end.next;
        Node cur = start;
        Node pre = null;
        Node next = null;

        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;

        }

        start.next = end;
    }

    private Node getIndexNode(Node head, int k) {
        if (head == null || k < 1) {
            return null;
        }
        for (int i = 1; i < k; i++) {
            head = head.next;
            if (head == null) {
                return null;
            }
        }
        return head;
    }


    public static void main(String[] args) {
        System.out.println(1);
    }



}
