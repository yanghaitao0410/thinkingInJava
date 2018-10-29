package com.yht.nowcode.tree.binary_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 满二叉树
 */
public class CompleteBinaryTree {

    /**
     * 判断一棵树是否是完全二叉树：是满二叉树或在成为满二叉树的道路上
     * 二叉树的一颗子树一共有4种情况：
     *      1.左右孩子都有
     *      2.有左孩子没右孩子
     *      3.无左孩子有右孩子
     *      4.左右孩子都没有
     * 判断条件:
     *      条件1：遍历过程中若出现情况3，直接返回false
     *      条件2：若发现当前节点的孩子不全：即情况2或4，则后面的节点必须是叶节点（没有孩子）最后返回true，发现后面节点不是叶节点返回false。
     *
     * @param head
     * @return
     */
    public static boolean isCompleteBinaryTree(TreeNode head) {
        if(head == null) {
            throw new RuntimeException("TreeNode null!!");
        }
        boolean flag = false;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        while(!queue.isEmpty()) {
            head = queue.poll();
            if(head.left == null && head.right != null) {
                return false;
            }
            if(head.left != null) {
                queue.add(head.left);
            }
            if(head.right != null) {
                queue.add(head.right);
            }
            if(head.left == null || head.right == null) {
                flag = true;
                continue;
            }
            if(flag == true && (head.left != null || head.right != null)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 已知一颗完全二叉树，求其节点的个数
     * 要求：时间复杂度低于O(N),N为这棵树的节点个数
     * 假设一棵满二叉树的高度为L，其节点的个数为(2^L - 1)
     *      思路：
     *          先遍历当前树的左边界，得出当前树的高度，然后判断当前树的右子树的左边是否和当前树的高度相等，
     *          若相等，则左子树为满二叉树，若不等，则右子树为满二叉树，通过公式求出部分节点个数，
     *          剩下的部分递归求出节点个数。
     * @param head
     */
    public static int getCompleteBinaryTreeNodeCount(TreeNode head) {
        int count = 0;
        if(head == null) {
            return count;
        }
        int height = getCompleteBinaryTreeHeight(head);
        int rh = getCompleteBinaryTreeHeight(head.right);
        if(height == rh + 1) { //右树高度和总体一样，左子数为满二叉树
            count = count + (int)(Math.pow(2, rh)) - 1 + 1 + getCompleteBinaryTreeNodeCount(head.right);
        } else if(height == rh + 2){ //右树高度和总体不一样，右子树为满二叉树
            count = count + (int)(Math.pow(2, rh)) - 1 + 1 + getCompleteBinaryTreeNodeCount(head.left);
        }
        return count;
    }

    /**
     * 获得树的高度
     * @param head
     * @return
     */
    private static int getCompleteBinaryTreeHeight(TreeNode head) {
        if(head == null) {
            return 0;
        }
        TreeNode cur = head;
        int height = 0;
        while (cur != null) {
            cur = cur.left;
            height++;
        }
        return height;
    }
}
