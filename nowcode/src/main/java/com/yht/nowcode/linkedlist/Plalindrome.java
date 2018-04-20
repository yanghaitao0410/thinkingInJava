package com.yht.nowcode.linkedlist;

import java.util.Stack;

/**
 * 判断一个链表是否为回文结构
 * 【题目】：给定一个链表的头节点head，请判断该链表是否为回文结构
 * 例如：
 * 1->2->1, 返回true
 * 1->2->2->1, 返回true
 * 15->6->15, 返回true
 * 1->2->3, 返回false
 * <p>
 * 如果链表长度为N，要求时间复杂度O(N), 额外空间复杂度O(1)
 */
public class Plalindrome {

    /**
     * 一般思路：时间复杂度O(N), 额外空间复杂度O(N)
     * 准备一个栈，然后遍历链表将数据压入该栈，最后再次遍历该链表，
     * 每遍历一个数据都跟栈弹出元素进行比较，
     * 若中间有不相同值，返回false,否则返回true
     *
     * @param root
     * @return
     */
    public static boolean plalindrome1(Node root) {
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }
        while (!stack.isEmpty()) {
            if (stack.pop().value != root.value) {
                return false;
            }
            root = root.next;
        }
        return true;
    }

    /**
     * 好一点的思路：时间复杂度O(N), 额外空间复杂度O(N) ，但实际省一半空间
     * 准备两个指针，一个快指针（移动一次走两步），一个慢指针（移动一次走一步）
     * 当快指针走到结尾的位置时，满指针正好指向该链表的中点位置
     * 然后将中点到结尾的数据依次压入栈，最后遍历
     * 链表开始位置到中点前一位置和栈比较
     * 若中间有不相同值，返回false,否则返回true
     *
     * @param root
     * @return
     */
    public static boolean plalindrome2(Node root) {
        Stack<Node> stack = new Stack<>();
        Node slow, fast;
        slow = fast = root;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Node cur = slow;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (!stack.isEmpty()) {
            if (stack.pop().value != root.value) {
                return false;
            }
            root = root.next;
        }
        return true;
    }

    /**
     * 优解思路：时间复杂度O(N), 额外空间复杂度O(1)
     * 准备两个指针，一个快指针（移动一次走两步），一个慢指针（移动一次走一步）
     * 当快指针走到结尾的位置时，满指针正好指向该链表的中点位置
     * 然后将该链表后半部分逆序（快指针向慢指针移动逆序，链表末端需要一个新指针指向）
     * 最后两个指针指向链表的开始位置和结束位置
     * 遍历两头到中间，若中间有不相同值，返回false,否则返回true
     * 注意: 在返回之前要将逆序的链表恢复到初始状态
     *
     * @param root
     * @return
     */
    public static boolean plalindrome3(Node root) {
        Node slow, fast;
        slow = fast = root;
        //fast一次走两步，增加fast.next != null 判断是为了防止第二个判断空指针
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //若链表的数目是偶数，fast最终会停在倒数第二个位置
        Node end = null;
        if(fast.next != null) {
            end = fast.next;
        }
        Node tail = reversalList(slow, end == null ? fast : end);
        Node head = root;
        while (head != null) {
            if (head.value != tail.value) {
                reversalList(end == null ? fast : end, slow);
                return false;
            }
            head = head.next;
            tail = tail.next;
        }
        reversalList(end == null ? fast : end, slow);
        return true;
    }

    /**
     * 将start位置到end位置的链表逆序
     * @param start
     * @param end
     * @return
     */
    public static Node reversalList(Node start, Node end) {
        Node pre = start; //逆序之后该位置即链表的结尾
        Node head = start.next;
        Node next;
        pre.next = null;  //将结尾的下一个赋值为null

        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


}












