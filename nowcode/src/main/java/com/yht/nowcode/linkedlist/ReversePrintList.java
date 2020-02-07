package com.yht.nowcode.linkedlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * 输入链表的第一个节点，从尾到头反过来打印出每个结点的值。
 * @author yht
 * @create 2018/12/3
 */
public class ReversePrintList {
    /**
     * 使用栈
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(Node listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.add(listNode.value);
            listNode = listNode.next;
        }
        ArrayList<Integer> ret = new ArrayList<>();
        while (!stack.isEmpty())
            ret.add(stack.pop());
        return ret;
    }

    /**
     * 使用递归
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead2(Node listNode) {
        ArrayList<Integer> ret = new ArrayList<>();
        if (listNode != null) {
            ret.addAll(printListFromTailToHead(listNode.next));
            ret.add(listNode.value);
        }
        return ret;
    }

    public ArrayList<Integer> printListFromTailToHead3(Node listNode) {
        // 头插法构建逆序链表
        Node head = new Node(-1);
        while (listNode != null) {
            Node memo = listNode.next;
            listNode.next = head.next;
            head.next = listNode;
            listNode = memo;
        }
        // 构建 ArrayList
        ArrayList<Integer> ret = new ArrayList<>();
        head = head.next;
        while (head != null) {
            ret.add(head.value);
            head = head.next;
        }
        return ret;
    }

    /**
     * 使用 Collections.reverse()
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead4(Node listNode) {
        ArrayList<Integer> ret = new ArrayList<>();
        while (listNode != null) {
            ret.add(listNode.value);
            listNode = listNode.next;
        }
        Collections.reverse(ret);
        return ret;
    }
}
