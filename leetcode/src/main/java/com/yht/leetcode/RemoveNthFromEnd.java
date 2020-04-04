package com.yht.leetcode;

import org.junit.Test;

import java.util.List;
import java.util.Stack;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * <p>
 * 说明：
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/3/26
 */
public class RemoveNthFromEnd {
    //      Definition for singly-linked list.


    @Test
    public void test() {
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(5);

        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        ListNode returnHead = removeNthFromEnd2(head, 2);
        while (returnHead != null) {
            System.out.printf("%s ", returnHead.val);
            returnHead = returnHead.next;
        }
    }


    /**
     * 解法1：利用栈的特性
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //定义一个亚节点 指向第一个元素
        ListNode preNode = new ListNode(-1);
        preNode.next = head;
        Stack<ListNode> stack = new Stack<>();
        ListNode current = preNode;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        //弹出n次
        for(int i = 0; i < n; i++) {
            stack.pop();
        }

        //此时的栈顶元素是n位置的前一个元素 将当前位置的下一个元素删除即可 即使要移除的元素是第一个元素 也能满足要求
        ListNode node = stack.pop();
        ListNode remove = node.next;
        node.next = remove.next;

        return preNode.next; //返回亚节点的下一个元素
    }

    /**
     * 解法2：官方解法 准备2个指针
     *  第一个指针先从head向后移动n个位置，第二个指针从亚节点开始，此时两个指针中间有n个位置
     *  然后2个指针同时向下移动，当第一个指针移动到末尾 == null 第二个指针正好在倒数第n - 1位置上 将下一个位置元素删去即可
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode preHead = new ListNode(0); //亚节点
        preHead.next = head;
        ListNode first = preHead;
        ListNode second = preHead;
        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        // Move first to the end, maintaining the gap
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return preHead.next;
    }

}
