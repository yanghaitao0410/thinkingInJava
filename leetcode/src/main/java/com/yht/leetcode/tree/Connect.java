package com.yht.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Desc
 * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 *
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 *
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author water
 * @date 2020/7/6
 **/
public class Connect {


// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}


    /**
     * 使用嵌套循环结构，该方法的每一步都需要记录当前队列中。全部元素数量，对应树中一个层级元素的数量。然后从队列中处理对应数量的元素。
     * 完成后，这一层级所有的节点都被访问，同时队列中剩下的数据包含下一层级的全部节点。
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {

        if (root == null) {
            return root;
        }

        // Initialize a queue data structure which contains
        // just the root of the tree
        Queue<Node> Q = new LinkedList<Node>();
        Q.add(root);

        // Outer while loop which iterates over
        // each level
        while (Q.size() > 0) {

            // Note the size of the queue
            int size = Q.size();

            // Iterate over all the nodes on the current level
            for(int i = 0; i < size; i++) {

                // Pop a node from the front of the queue
                Node node = Q.poll();

                // This check is important. We don't want to
                // establish any wrong connections. The queue will
                // contain nodes from 2 levels at most at any
                // point in time. This check ensures we only
                // don't establish next pointers beyond the end
                // of a level
                if (i < size - 1) {
                    node.next = Q.peek();
                }

                // Add the children, if any, to the back of
                // the queue
                if (node.left != null) {
                    Q.add(node.left);
                }
                if (node.right != null) {
                    Q.add(node.right);
                }
            }
        }

        // Since the tree has now been modified, return the root node
        return root;
    }

    /**
     *使用已建立的 next 指针
     *
     * 从根节点开始，由于第 0 层只有这一个节点，所以不需要连接。直接为第 1 层节点建立 next 指针即可。
     * 该算法中需要注意的一点是，当我们为第 N 层节点建立 next 指针时，处于第 N-1层。
     * 当第 N 层节点的 next 指针全部建立完成后，移至第 N 层，建立第 N+1 层节点的 next 指针。
     *
     * 遍历某一层的节点时，这层节点的 next 指针已经建立。因此我们只需要知道这一层的最左节点，就可以按照链表方式遍历，不需要使用队列。
     *
     * @param root
     * @return
     */
    public Node connect2(Node root) {
        Node almostLeft = root;
        while(almostLeft != null) {
            Node head = almostLeft;
            Node nextAlmostLeft = null;
            while(head != null) {
                if(head.left != null) {
                    head.left.next = head.right;
                    if(nextAlmostLeft == null) {
                        nextAlmostLeft = head.left;
                    }
                }
                if(head.right!= null) {
                    if(nextAlmostLeft == null) {
                        nextAlmostLeft = head.right;
                    }
                    if(head.next != null) {
                        head.right.next = head.next.left;
                    }
                }
                head = head.next;
            }
            almostLeft = nextAlmostLeft;
        }
        return root;
    }
}
