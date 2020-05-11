package com.yht.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
 * <p>
 * 示例:
 * <p>
 * 输入: 3
 * 输出:
 * [
 *   [1,null,3,2],
 *   [3,2,null,1],
 *   [3,1,null,null,2],
 *   [2,1,3],
 *   [1,null,2,null,3]
 * ]
 * 解释:
 * 以上的输出对应以下 5 种不同结构的二叉搜索树：
 * <p>
 * 1         3     3      2      1
 *  \       /     /      / \      \
 *   3     2     1      1   3      2
 *  /     /       \                 \
 *2     1         2                 3
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-binary-search-trees-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/4/16
 */
public class GenerateTrees {


    //      Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 我们可以利用一下二叉树搜索树的性质。左子树的所有值小于根节点，右子树的所有值大于根节点。
     * 所以如果求 1...n数字组成二叉搜索树的所有可能。
     * 我们只需要汇总1~n所有数字当作根节点的所有可能：
     * 1 作为根节点，[] 空作为左子树，[2 ... n] 的所有可能作为右子树。
     * 2 作为根节点，[1] 作为左子树，[3...n] 的所有可能作为右子树。
     * 3 作为根节点，[1 2] 的所有可能作为左子树，[4 ... n] 的所有可能作为右子树，然后左子树和右子树两两组合。
     * 4 作为根节点，[1 2 3] 的所有可能作为左子树，[5 ... n] 的所有可能作为右子树，然后左子树和右子树两两组合。
     * ...
     * n 作为根节点，[1... n] 的所有可能作为左子树，[] 作为右子树。
     *
     * 至于[2 ... n] 的所有可能以及 [4 ... n]以及其他情况的所有可能，
     * 可以利用上边的方法递归，把每个数字作为根节点，然后把所有可能的左子树和右子树组合起来即可。
     *
     * 如果只有一个数字，那么所有可能就是一种情况，把该数字作为一棵树。而如果是 []，那就返回 null。
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            List list = new ArrayList();

            return list;
        }
        return getAns(1, n);
    }

    private List<TreeNode> getAns(int start, int end) {
        List<TreeNode> nodeList = new ArrayList<>();
        if (start > end) {
            nodeList.add(null);
            return nodeList;
        }
        if (start == end) {
            TreeNode node = new TreeNode(start);
            nodeList.add(node);
            return nodeList;
        }

        //从start到end 每个数字都当作头节点
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftNodeList = getAns(start, i - 1); //获取当前节点左边数的全部结果
            List<TreeNode> rightNodeList = getAns(i + 1, end); //获取当前节点右边数的全部结果


            for (TreeNode left : leftNodeList) { //排列组合左右子树
                for (TreeNode right : rightNodeList) {
                    TreeNode node = new TreeNode(i);
                    node.left = left;
                    node.right = right;
                    nodeList.add(node);
                }
            }
        }
        return nodeList;
    }
}
