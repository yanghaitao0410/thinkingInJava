package com.yht.leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author yht
 * @create 2020/7/15
 */
public class BSTIterator {

    // Definition for a binary tree node.
      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }

    List<Integer> list;
    Integer current = 0;

    public BSTIterator(TreeNode root) {
        list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if(node.left != null) {
                stack.push(node.left);
            } else {
                list.add(node.val);
                stack.pop();
                if(node.right != null) {
                    stack.push(node.right);
                }
            }
        }
    }

    /** @return the next smallest number */
    public int next() {
        return list.get(current++);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return current < list.size();
    }
}
