package com.yht.leetcode.tree;

/**
 * @Desc
 * @Author water
 * @date 2020/7/3
 **/
public class MinDepth {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int minDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return getMinTreeDepth(root);
    }

    public int getMinTreeDepth(TreeNode root) {
        if(root.left == null && root.right == null) {
            return 1;
        }

        int leftDepth = Integer.MAX_VALUE;
        int rightDepth = Integer.MAX_VALUE;

        if(root.left != null) {
           leftDepth = 1 + getMinTreeDepth(root.left);
        }
        if(root.right != null) {
            rightDepth = 1 + getMinTreeDepth(root.right);
        }

        return Math.min(leftDepth, rightDepth);
    }
}
