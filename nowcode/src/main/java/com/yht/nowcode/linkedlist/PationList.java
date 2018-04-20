package com.yht.nowcode.linkedlist;

/**
 * 将单向链表按某值划分左边小，中间相等，右边大的形式
 * 【题目】
 *  给定一个单向链表的头节点head，节点的值类型是整形，再给定一个整数pivot。
 *  实现一个调整链表的函数，将链表调整为左部分都是值小于pivot的节点，中间部分都是值等于pivot的节点，
 *  右部分都是值大于pivot的节点。除这个要求外，对调整后的节点顺序没有更多要求。
 *  例如：链表 9->0->4->5->1, pivot = 3
 *  调整后的链表可以是1->0->4->9->5,也可以是0->1->9->5->4。
 *  总之，满足左部分都是值小于3的节点，中间部分都是值等于3的节点，
 *  右部分都是值大于3的节点即可。对某部分内部的节点顺序不做要求
 */
public class PationList {

    /**
     * 简单实现方式：
     *      先将节点放入数组中，然后pation数组，最后遍历数组拼成链表返回
     *      时间复杂度O(N),额外空间复杂度O(N)
     * @param head
     * @return
     */
    public static Node pationList1(Node head, int pivot) {
        int size = 0;
        Node cur = head;
        while(cur != null) {
            size++;
            cur = cur.next;
        }

        Node[] nodes = new Node[size];
        cur = head;
        for(int i = 0; i < size; i++) {
            nodes[i] = cur;
            cur = cur.next;
            nodes[i].next = null;
        }
        pationNodeArr(nodes, pivot);

        return Arr2List(nodes);
    }

    private static void pationNodeArr(Node[] nodes, int pivot) {
        int less = -1, more = nodes.length;  //一开始小于区和大于区在数组边界外
        int i = 0;
        while(i < more && less < more) {
            if(nodes[i].value < pivot) {
                swop(nodes, ++less, i++);
            }else if(nodes[i].value == pivot) {
                i++;
                continue;
            }else {
                swop(nodes, --more, i);
            }
        }
    }

    private static Node Arr2List(Node[] nodes) {
        Node head = nodes[0];
        Node cur = head;

        for(int i = 1; i < nodes.length; i++) {
            cur.next = nodes[i];
            cur = cur.next;
        }
        return head;
    }

    private int getRandomIndex(int size) {
        return (int) (Math.random() * size);
    }


    private static void swop(Node[] nodes, int i, int j) {
        Node tmp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = tmp;
    }

    /**
     * 优解方案：时间复杂度O(N),额外空间复杂度O(1)
     * @param head
     * @return
     */
    public Node PationList2(Node head) {

        return null;
    }

}
