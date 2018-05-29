package com.yht.nowcode.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * 两链表相交的一系列问题：
 * 在本题中，单链表可能有环、可能无环。给定连个单链表的头节点head1，head2，这两个链表可能相交，也可能不相交。
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点；如果不相交，返回null即可。
 * 要求：如果链表1的长度为M，链表2的长度为N，时间复杂度请达到O(N + M),额外空间复杂度请达到O(1)
 * <p>
 * 思路：需要先通过Node firstLoopNode(Node head)判断这两个链表是否有环:有如下几种情况：
 * 第一种情况：若head1返回结果为null，head2返回结果为null----->两个链表都是单链表
 * 这两个链表只有两种可能性：相交或平行
 * 解法1：将head1放入Set集合中，然后遍历head2，若遍历过程中发现Set中已经存在当前节点---->相交 返回当前节点
 * 否则平行
 */
public class LoopList {

    public static Node get2NodeFirstLoop(Node head1, Node head2) {
        Node node1 = firstLoopNode2(head2);
        Node node2 = firstLoopNode2(head2);

        if (node1 == null && node2 == null) {
            return get2NoLoopFirstNode1(head1, head2);
            //return get2NoLoopFirstNode2(head1, head2);
        }
        return null;
    }

    /**
     * 两个单链表 只有两种可能性：相交或平行
     * 解法1：将head1放入Set集合中，然后遍历head2，若遍历过程中发现Set中已经存在当前节点---->相交 返回当前节点
     * 否则平行
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node get2NoLoopFirstNode1(Node head1, Node head2) {
        Set<Node> nodeSet = new HashSet<>();
        Node curNode = head1;
        while (curNode != null) {
            nodeSet.add(curNode);
            curNode = curNode.next;
        }
        curNode = head2;
        while (curNode != null) {
            if (nodeSet.contains(curNode)) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    /**
     * 两个单链表 返回第一个相交节点：
     * 解法2：分别遍历这两条链表，记录这两条链表的长度M, N 和最后的节点endNode1, endNode2
     * 若endNode1 == endNode2 --->相交， 然后较长的链表先走减去短链表的长度，然后这两个链表共同向下走，
     * 并判断当前节点是否相等，相等的节点即为第一个相交节点。
     * eg:head1长度100， head2长度80，那么head1先走20步，然后共同向下移动，并判断节点是否相同
     *
     * @return
     */
    public static Node get2NoLoopFirstNode2(Node head1, Node head2) {
        Object[] nodeArr1 = getNodeInfo(head1);
        Object[] nodeArr2 = getNodeInfo(head2);
        if (nodeArr1[1] != nodeArr2[1]) {
            return null;
        }
        int count1 = Integer.parseInt(nodeArr1[0] + "");
        int count2 = Integer.parseInt(nodeArr2[0] + "");
        if (count1 > count2) {
            return getNode(head1, head2, count1, count2);
        }else {
            return getNode(head2, head1, count2, count1);
        }
    }

    /**
     * 获得链表的长度和最后一个节点
     * @param head
     * @return Object[2]{length, endNode}
     */
    public static Object[] getNodeInfo(Node head) {
        int count = 0;
        Node endNode = null;
        while (head != null) {
            count++;
            endNode = head;
            head = head.next;
        }
        return new Object[]{count, endNode};
    }

    /**
     *较长链表先移动至和短链表相同的长度，然后这两条链表共同移动，并比较节点是否是同一个
     * @param maxLengthNode
     * @param minLengthNode
     * @return
     */
    public static Node getNode(Node maxLengthNode, Node minLengthNode, int maxCount, int minCount) {
        int distance = maxCount - minCount;
        while (distance > 0) {
            maxLengthNode = maxLengthNode.next;
            distance--;
        }
        while (maxLengthNode != null) {
            if (maxLengthNode == minLengthNode) {
                return  maxLengthNode;
            }
            maxLengthNode = maxLengthNode.next;
            minLengthNode = minLengthNode.next;
        }
        return  null;
    }

    /**
     * 给定一个链表，可能有环可能无环，
     * 若有环返回入环的第一个节点，无环返回null
     * 这里比较的都是内存地址而不是值
     * <p>
     * 解法一:
     * 遍历整个链表，将每一个元素放到Set集合中，放之前先判断在该集合中是否已经存在该节点了，存在直接返回
     *
     * @param head
     * @return
     */
    public static Node firstLoopNode1(Node head) {
        Node cur = head;
        Set<Node> nodeSet = new HashSet<>();

        while (cur != null) {
            if (nodeSet.contains(cur)) {
                return cur;
            } else {
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
     * 再次相遇的节点即为入环第一个节点(数学结论)
     *
     * @param head
     * @return
     */
    public static Node firstLoopNode2(Node head) {
        Node fastNode = head, slowNode = head.next;
        if (fastNode.next == null || fastNode.next.next == null) {
            return null;
        }
        fastNode = head.next.next;

        while (fastNode != slowNode) {
            if (fastNode.next == null || fastNode.next.next == null) {
                return null;
            }
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }

        fastNode = head;
        while (fastNode != slowNode) {
            fastNode = fastNode.next;
            slowNode = slowNode.next;
        }

        return fastNode;
    }
}
