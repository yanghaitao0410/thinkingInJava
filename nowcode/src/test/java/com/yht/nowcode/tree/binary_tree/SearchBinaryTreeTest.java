package com.yht.nowcode.tree.binary_tree;

import org.junit.Before;
import org.junit.Test;

public class SearchBinaryTreeTest {
    TreeNode head;

    @Before
    public void before() {
        head = new TreeNode(7);
        TreeNode node4 = new TreeNode(4);
        TreeNode node9 = new TreeNode(9);
        head.left = node4;
        head.right = node9;
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        node4.left = node3;
        node4.right = node5;
        TreeNode node8 = new TreeNode(8);
        TreeNode node10 = new TreeNode(10);
        node9.left = node10;
        node9.right = node8;
    }

    @Test
    public void test() {
        BinaryTree.inTraverse(head);
        System.out.printf("isSearchBinaryTree : %b", SearchBinaryTree.isSearchBinaryTree(head));
    }


}
