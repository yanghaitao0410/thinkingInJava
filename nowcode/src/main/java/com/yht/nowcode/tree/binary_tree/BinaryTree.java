package com.yht.nowcode.tree.binary_tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    /**
     * 二叉树的先序遍历(先遍历头节点)：中左右
     * 递归版：
     * 先判断非空条件，若head为空，return
     * step1:打印头节点
     * step2:先序遍历头节点左子树
     * step3:先序遍历头节点右子数
     * @param head
     */
    public static void preTraverseRecursion(TreeNode head) {
        if(head == null) {
            return;
        }
        System.out.printf("%d " , head.val);
        preTraverseRecursion(head.left);
        preTraverseRecursion(head.right);
    }

    /**
     * 先序遍历非递归版
     * 借助栈来实现
     * 首先判断head非空
     * 将head压入栈中
     * 循环栈，条件非空
     *     将栈顶元素弹出打印
     *     将弹出元素的右节点压入
     *     将弹出元素的左节点压入
     * @param head
     */
    public static void preTraverse(TreeNode head) {
        if (head == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode tmp = stack.pop();
            System.out.printf("%d ", tmp.val);
            if(tmp.right != null) {
                stack.push(tmp.right);
            }
            if(tmp.left != null) {
                stack.push(tmp.left);
            }
        }
    }

    /**
     * 二叉树中序遍历（中间遍历头节点）：左中右
     * 递归版：
     * 先判断非空条件，若head为空，return
     * step1:中序遍历头节点左子树
     * step2:打印头节点
     * step3:中序遍历头节点右子数
     *
     * @param head
     */
    public static void inTraverseRecursion(TreeNode head) {
        if(head == null) {
            return;
        }
        inTraverseRecursion(head.left);
        System.out.printf("%d ",head.val);
        inTraverseRecursion(head.right);
    }

    /**
     * 二叉树中序遍历：左中右
     * 借助栈来实现
     * 首先判断head非空
     * 循环栈，栈非空 或 head非空（此条件是为了压入根节点）只要压入一个元素，就会将head的元素全部压入
     *  若head不为空，将head压入栈，head像左移动
     *  否则，此刻head走到了二叉树某一条路径的叶子节点的尽头，
     *  弹出栈顶元素并打印（此时该节点的左边已经全部入栈，接下来该将当前节点的右子树入栈）
     *  head = head.right
     *
     * @param head
     */
    public static void inTraverse(TreeNode head) {
        if(head == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || head!= null) {
            if(head != null) { //每一个节点都当作一颗树来看待，先压入当前节点，然后往当前节点的左节点移动，最后栈顶元素就是应该先打印的节点
                stack.push(head);
                head = head.left;
            } else { //打印栈顶元素（当前节点当作一棵树），然后向当前节点的右子树移动
                head = stack.pop();
                System.out.printf("%d ", head.val);
                head = head.right;
            }
        }
        System.out.println();
    }

    /**
     * 二叉树后序遍历（最后遍历头节点）：左右中
     * 递归版：
     * 先判断非空条件，若head为空，return
     * step1:后序遍历头节点左子树
     * step2:后序遍历头节点右子数
     * step3:打印头节点
     *
     * @param head
     */
    public static void posTraverseRecursion(TreeNode head) {
        if(head == null) {
            return;
        }
        posTraverseRecursion(head.left);
        posTraverseRecursion(head.right);
        System.out.printf("%d ",head.val);
    }

    /**
     * 二叉树后序遍历非递归版：左右中
     * 准备一个辅助栈help
     * 将二叉树前序遍历入栈子树顺序颠倒 中左右 --> 中右左
     * 本来前序遍历应该弹出的地方改为入辅助栈
     * 最后将辅助栈中全部弹出打印  左右中
     *
     * @param head
     */
    public static void posTraverse(TreeNode head) {
        if(head == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> help = new Stack();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode tmp = stack.pop();
            help.push(tmp);
            //因为出栈顺序和入站相反，为了得到先右后左
            //这里入栈顺序为先左后右
            if(tmp.left != null) {
                stack.push(tmp.left);
            }
            if(tmp.right!= null) {
                stack.push(tmp.right);
            }
        }
        while(!help.isEmpty()) {
            System.out.printf("%d ", help.pop().val);
        }
    }

    /**
     * 二叉树按层级遍历
     * 准备一个队列，头节点如队列，然后出队列打印，然后头节点的左节点右节点依次入队列
     * @param head
     */
    public static void layerTraversing(TreeNode head) {
        if(head == null) {
            throw new RuntimeException("TreeNode null!!");
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        while(!queue.isEmpty()) {
            head = queue.poll();
            System.out.printf("%d ", head.val);
            if(head.left != null) {
                queue.add(head.left);
            }
            if(head.right != null) {
                queue.add(head.right);
            }
        }
    }



}
