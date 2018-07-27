package com.yht.nowcode.binary_tree;

import org.junit.Before;
import org.junit.Test;

public class BinaryTreeTest {
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
    public void preTraverseRecursionTest() {
        BinaryTree.preTraverseRecursion(head);
    }

    @Test
    public void preTraverseTest() {
        BinaryTree.preTraverse(head);
        System.out.println();
        BinaryTree.preTraverseRecursion(head);
    }

    @Test
    public void inTraverseRecursionTest() {
        BinaryTree.inTraverseRecursion(head);
    }

    @Test
    public void inTraverse() {
        BinaryTree.inTraverse(head);
        System.out.println();
        BinaryTree.inTraverseRecursion(head);
    }

    @Test
    public void posTraverseRecursionTest() {
        BinaryTree.posTraverseRecursion(head);
    }

    @Test
    public void posTraverseTest() {
        BinaryTree.posTraverse(head);
        System.out.println();
        BinaryTree.posTraverseRecursion(head);
    }

}
