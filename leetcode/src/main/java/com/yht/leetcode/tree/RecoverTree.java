package com.yht.leetcode.tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉搜索树中的两个节点被错误地交换。
 * 请在不改变其结构的情况下，恢复这棵树。
 * 示例 1:
 * 输入: [1,3,null,null,2]
 * <p>
 *    1
 *   /
 *  3
 *   \
 *    2
 * 输出: [3,1,null,null,2]
 *    3
 *   /
 *  1
 *   \
 *    2
 * <p>
 * 示例 2:
 * 输入: [3,1,4,null,null,2]
 * 3
 * / \
 * 1   4
 *    /
 *   2
 * <p>
 * 输出: [2,1,4,null,null,3]
 * 2
 * / \
 * 1   4
 *    /
 *  3
 * <p>
 * 进阶:
 * 使用 O(n) 空间复杂度的解法很容易实现。
 * 你能想出一个只使用常数空间的解决方案吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/recover-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/5/5
 */
public class RecoverTree {
    //    *
//     * Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }

    private List<TreeNode> nodeList = new LinkedList<>();

    /**
     * 中序遍历得到数组
     * 然后找到数组中交换位置的2个节点
     * 最后把这2个节点交换回去
     * @param root
     */
    public void recoverTree(TreeNode root) {
        inOrder(root);
        int[] swop = find2SwopIndex();
        swopNodeVal(swop[0], swop[1]);
    }

    private void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        nodeList.add(node);
        inOrder(node.right);
    }

    /**
     * 找到2个交换节点的位置
     * 判断i位置的元素是否比i + 1位置的大
     * 若只有1处是这样 说明交换位置就是i 和i+ 1
     * 若有2处是这样 第一个节点需要取第一处的第一个元素   第2个节点需要取第二处的第二个节点
     * 例如： 1 5 3 4 2 6   （2 和5位置交换）
     *
     * @return
     */
    private int[] find2SwopIndex() {
        int x = -1, y = -1;
        for (int i = 0; i < nodeList.size() - 1; i++) {
            if (nodeList.get(i).val > nodeList.get(i + 1).val) {
                if (x == -1) {
                    x = i;
                }
                y = i + 1;
            }
        }
        return new int[]{x, y};
    }

    private void swopNodeVal(int index1, int index2) {
        int tmp = nodeList.get(index1).val;
        nodeList.get(index1).val = nodeList.get(index2).val;
        nodeList.get(index2).val = tmp;
    }

    @Test
    public void test() {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(2);
        root.left = node1;
        node1.right = node2;

        recoverTree(root);

        System.out.println(nodeList);
    }

}
