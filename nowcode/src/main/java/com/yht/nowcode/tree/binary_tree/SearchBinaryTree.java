package com.yht.nowcode.tree.binary_tree;

import java.util.Stack;

/**
 * 搜索二叉树 ：对于任意一个节点，左子树都比它小，右子树都比他大
 *  （一般不出现重复节点）可以将多个属于同一个节点的数据压缩到一个节点中，可以list存储
 * 如何判断一棵树是搜索二叉树？
 *  二叉树的中序遍历的节点依次升序，即为搜索二叉树
 */
public class SearchBinaryTree {

    public static boolean isSearchBinaryTree(TreeNode head) {
        if(head == null) {
            return false;
        }
        int preValue = Integer.MIN_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || head != null) {
            if(head != null) {
                stack.push(head);
                head = head.left;
            } else{
                head = stack.pop();
                if(preValue >= head.val) {
                    return false;
                }
                preValue = head.val;
                head = head.right;
            }
        }
        return true;
    }

}
