package com.yht.leetcode;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 * <p>
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回其自底向上的层次遍历为：
 * <p>
 * [
 * [15,7],
 * [9,20],
 * [3]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/2/26
 */
public class LevelOrderBottom {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    TreeNode root;

    @Before
    public void before() {
        root = new TreeNode(3);
        TreeNode n1 = new TreeNode(9);
        TreeNode n2 = new TreeNode(20);
        TreeNode n3 = new TreeNode(15);
        TreeNode n4 = new TreeNode(7);

        root.left = n1;
        root.right = n2;

        n2.left = n3;
        n2.right = n4;
    }

    @Test
    public void test() {
        List<List<Integer>> result = levelOrderBottom(root);
        result.stream().forEach(item -> {
            item.stream().forEach(it -> {System.out.printf("%s ", it);});
            System.out.println();
        });
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null) {
            result.add(new ArrayList<>());
            return result;
        }
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 0));
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            Integer level = pair.getValue();
            List<Integer> list;
            if (level >= result.size()) {
                list = new ArrayList<>();
                result.add(list);
            } else {
                list = result.get(level);
            }
            list.add(node.val);
            if(node.right != null) {
                stack.push(new Pair<>(node.right, level + 1));
            }
            if(node.left != null) {
                stack.push(new Pair<>(node.left, level + 1));
            }
        }
        Collections.reverse(result);
       return result;
    }


}
