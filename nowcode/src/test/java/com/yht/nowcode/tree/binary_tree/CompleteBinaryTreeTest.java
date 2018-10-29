package com.yht.nowcode.tree.binary_tree;

import org.junit.Before;
import org.junit.Test;

public class CompleteBinaryTreeTest {

    TreeNode head;

    @Before
    public void before() {
        head = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        head.left = node2;
        head.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
    }

    @Test
    public void isCompleteBinaryTreeTest() {
        BinaryTree.layerTraversing(head);
        System.out.println();
        System.out.println(CompleteBinaryTree.isCompleteBinaryTree(head));
    }

    @Test
    public void getCompleteBinaryTreeNodeCount(){
        if(CompleteBinaryTree.isCompleteBinaryTree(head)) {
            System.out.println("count:" + CompleteBinaryTree.getCompleteBinaryTreeNodeCount(head));
        } else {
            System.out.println("The current tree is not a complete binary tree！");
        }
    }
}
