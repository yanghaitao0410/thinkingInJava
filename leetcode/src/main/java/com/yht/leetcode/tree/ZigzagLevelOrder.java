package com.yht.leetcode.tree;

import java.util.*;

/**
 * @Desc 二叉树的锯齿形层次遍历
 * <p>
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 * <p>
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * <p>
 * 返回锯齿形层次遍历如下：
 * [
 * [3],
 * [20,9],
 * [15,7]
 * ]
 * @Author water
 * @date 2020/6/30
 **/
public class ZigzagLevelOrder {

    //Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 广度优先遍历二叉树，通过一个标志位判断当前层是从左到右遍历还是从右到左遍历
     * 从右到左还是使用了从左到右遍历，只不过是使用双端队列，将新的元素插入到队列头部
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Set<TreeNode> set = new HashSet<>();

        //广度优先遍历双端队列
        Deque<TreeNode> nodeDueue = new LinkedList<>();

        set.add(root);
        nodeDueue.add(root);
        nodeDueue.add(null);//使用空节点来隔开不同的层

        //是否是从左向右遍历
        boolean left2Right = true;

        //要返回的每层list
        LinkedList<Integer> levelList = new LinkedList<>();

        while (!nodeDueue.isEmpty()) {
            TreeNode node = nodeDueue.pollFirst();
            if (node == null) {
                result.add(levelList);
                levelList = new LinkedList<>();
                left2Right = !left2Right;
                if (!nodeDueue.isEmpty()) {
                    //队列不空说明有下次循环，会入队队头的下一层的节点 所以在这里向队列加入空节点
                    nodeDueue.addLast(null);
                }
            } else {
                if (left2Right) {
                    levelList.addLast(node.val);
                } else { //从右到左 插入到返回队列的头部
                    levelList.addFirst(node.val);
                }
                //入队下层节点
                if (node.left != null) {
                    nodeDueue.addLast(node.left);
                }
                if (node.right != null) {
                    nodeDueue.addLast(node.right);
                }
            }
        }

        return result;
    }

    /**
     * 递归实现 深度优先遍历 通过节点和所在的层定位要放的层
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        DFS(root, 0, result);
        return result;
    }

    public void DFS(TreeNode node, Integer level, List<List<Integer>> result) {
        //level从0开始 出现大于登录result个数的情况说明result中没有该层的list
        if(level >= result.size()) {
            List<Integer> levelList = new LinkedList<>();
            levelList.add(node.val);
            result.add(levelList);
        } else {
            //level从0开始 偶数层需要反向插入
            if(level % 2 == 0) {
                result.get(level).add(node.val);
            } else {
                result.get(level).add(0, node.val);
            }
        }
        //处理当前节点的子节点
        if(node.left != null) {
            DFS(node.left, level + 1, result);
        }

        if(node.right != null) {
            DFS(node.right, level + 1, result);
        }
    }

}
