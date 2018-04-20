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
     *
     *  思路：准备3个引用：small、equal、big
     *  遍历整个链表，找到最找出现的小于值、相等值、大于值然后让上面的3个引用指向他们
     *          例如：7->3->4->6->0->4     比较值为4
     *          small->3->0
     *          equal->4->4
     *          big->7->6
     *  重新遍历链表，若遍历到的节点已经被引用指向了，跳过（注意是内存地址相等==）
     *  出现的其他节点挂在上面对应3条子链表的末端
     *  最后将这3个链表连接
     *  需要考虑某一种值没有的情况、只有一种值的情况
     * @param head
     * @return
     */
    public static Node pationList2(Node head, int pivot) {
        Node small = null, equal = null, big = null;
        Node sEnd = null, eEnd = null, bEnd = null;
        Node cur = head;

        /**
         * 在子链表上挂Node
         */
        while(cur != null) {
            if(pivot < cur.value && big == null) {
                big = cur;
                bEnd = cur;
            } else if(pivot < cur.value && big != null) {
                bEnd.next = cur;
                bEnd = cur;
            }
            if(pivot == cur.value && equal == null) {
                equal = cur;
                eEnd = cur;
            }else if(pivot == cur.value && equal != null) {
                eEnd.next = cur;
                eEnd = cur;
            }
            if(pivot > cur.value && small == null) {
                small = cur;
                sEnd = cur;
            }else if(pivot > cur.value && small != null) {
                sEnd.next = cur;
                sEnd = cur;
            }
            cur = cur.next;
        }

        /**
         * 若只有一种值，直接返回对应区间的子节点
         */
        if(small != null && big == null && equal == null) {
            sEnd.next = null;
            return small;
        }
        if(small == null && big != null && equal == null) {
            bEnd.next = null;
            return big;
        }
        if(small != null && big == null && equal != null) {
            eEnd.next = null;
            return equal;
        }
        /**
         * 出现两种值以上，拼接
         */
        if(small != null) {
            head = small;
            if(equal != null) {
                sEnd.next = equal;
                if(big != null) {
                    eEnd.next = big;
                    bEnd.next = null;
                }else {
                    eEnd.next = null;
                }
            }else {
                sEnd.next = big;
                bEnd.next = null;
            }
        }else {
            head = equal;
            eEnd.next = big;
            bEnd.next = null;
        }
        return head;
    }

}
