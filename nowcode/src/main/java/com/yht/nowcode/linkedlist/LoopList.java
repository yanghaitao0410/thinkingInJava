package com.yht.nowcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表，可能有环可能无环，
 * 若有环返回入环的第一个节点，无环返回null
 * 这里比较的都是内存地址而不是值
 */
public class LoopList {

    /**
     * 遍历整个链表，将每一个元素放到Set集合中，放之前先判断在该集合中是否已经存在该节点了，存在直接返回
     * @param head
     * @return
     */
    public static Node firstLoopNode1(Node head) {
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

    /**
     * 解法2：准备两个指针，快指针一次走两步，慢指针一次走一步
     * 若快指针指向空，说明该链表无环，返回null
     * 若有环，当快指针和慢指针相遇，快指针返回头节点，
     * 之后快指针和慢指针都一次走一步，
     * 再次相遇的节点即为入环第一个节点
     * @param head
     * @return
     */
    public static Node firstLoopNode2(Node head) {
        Node fastNode = head, slowNode = head.next;
        if(fastNode.next == null || fastNode.next.next == null) {
            return null;
        }
        fastNode = head.next.next;

        while(fastNode != slowNode) {
            if(fastNode.next == null || fastNode.next.next == null) {
                return null;
            }
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }

        fastNode = head;
        while(fastNode != slowNode) {
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }
        
        return fastNode;
    }
}
