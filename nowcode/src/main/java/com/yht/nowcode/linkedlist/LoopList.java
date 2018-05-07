package com.yht.nowcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表，可能有环可能无环，
 * 若有环返回入环的第一个节点，无环返回null
 */
public class LoopList {

    /**
     * 遍历整个链表，将每一个元素放到Set集合中，放之前先判断在该集合中是否已经存在该节点了，存在直接返回
     * @param head
     * @return
     */
    public static Node firstLoopNode(Node head) {
        Node cur = head;
        Set<Node> nodeSet = new HashSet<>();

        while (cur != null) {
            if(nodeSet.contains(cur)) {
                return cur;
            }else {
                nodeSet.add(cur);
            }
            cur = cur.next;
        }

        return null;
    }
}
