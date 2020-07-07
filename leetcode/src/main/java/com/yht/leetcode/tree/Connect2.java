package com.yht.leetcode.tree;

import org.junit.Test;

/**
 * @author yht
 * @create 2020/7/7
 */
public class Connect2 {

    // Definition for a Node.
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    ", next=" + next +
                    '}';
        }
    }

    @Test
    public void testConnect() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node7 = new Node(7);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.right = node7;

        System.out.println(connect(node1));
    }

    public Node connect(Node root) {
        Node almostLeft = root;
        //根节点的next为空
        //遍历下一层的next
        while (almostLeft != null) {
            //下一层的最左元素
            Node nextAlmostLeft = null;
            Node node = almostLeft;
            Node preSubNode = null; //node下一层遍历节点的前一个节点  next节点为当前节点
            //每一层的next节点连接
            while (node != null) {
                //连接当前节点的左子节点
                if (node.left != null) {
                    //使用preSubNode连接next节点
                    if (preSubNode != null) {
                        preSubNode.next = node.left;
                        preSubNode = node.left;
                    } else {
                        preSubNode = node.left;
                    }
                    //nextAlmostLeft为null说明当前遍历到的子节点是下一层的第一个节点
                    if (nextAlmostLeft == null) {
                        nextAlmostLeft = node.left;
                    }
                }

                //连接当前节点的右子节点
                if (node.right != null) {
                    if (preSubNode != null) {
                        preSubNode.next = node.right;
                        preSubNode = node.right;
                    } else {
                        preSubNode = node.right;
                    }
                    if (nextAlmostLeft == null) {
                        nextAlmostLeft = node.right;
                    }
                }
                node = node.next;
            }
            //跳转到下一层
            almostLeft = nextAlmostLeft;
        }

        return root;
    }
}
