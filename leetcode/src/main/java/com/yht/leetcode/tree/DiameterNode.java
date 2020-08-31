package com.yht.leetcode.tree;

import org.junit.Test;

/**
 * @Desc
 * @Author water
 * @date 2020/8/19
 **/
public class DiameterNode {

    //最长直径节点数量
    static int maxDiameterNodeCount = 0;


    @Test
    public void test() {
        TreeNode node = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node.left = node2;
        node.right = node3;

        System.out.println(diameterOfBinaryTree(node));
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null) {
            return maxDiameterNodeCount;
        }
        getDiameterNodeCount(root);
        //直径长度为节点数量减一
        return maxDiameterNodeCount - 1;
    }

    /**
     * 递归得出每个节点作为根的最长边
     * @param root
     * @return
     */
    public int getDiameterNodeCount(TreeNode root) {
        if(root == null) {
            return 0;
        }

        if(root.left == null && root.right == null) {
            if (maxDiameterNodeCount < 1) {
                maxDiameterNodeCount = 1;
            }
            return 1;
        }
        int leftCount = getDiameterNodeCount(root.left);
        int rightCount = getDiameterNodeCount(root.right);

        //当前节点作为根节点的最长直径
        int nodeCount = 1 + leftCount + rightCount;
        maxDiameterNodeCount = Math.max(maxDiameterNodeCount, nodeCount);

        //返回较长的边
        return Math.max(rightCount, leftCount) + 1;
    }
}
