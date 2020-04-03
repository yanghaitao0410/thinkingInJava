package com.yht.nowcode.tree.binary_tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

public class BinaryTreeTest {
    TreeNode head;

    @Before
    public void before() {
        head = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(3);
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

    @Test
    public void layerTraversingTest() {
        BinaryTree.layerTraversing(head);
    }


    @Test
    public void testMirror() {
        System.out.println(isSymmetric(head));
    }

    private boolean isSymmetric(TreeNode root) {
        String midStr = mid(root);
        System.out.println(midStr);
        int midLength = midStr.length();
        for(int i = 0; i <= midLength / 2; i++) {
            if(midStr.charAt(i) != midStr.charAt(midLength - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    private String mid(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;

        while(!stack.isEmpty() || node != null) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                builder.append(node.val);
                node = node.right;
            }
        }
        return builder.toString();
    }

}
