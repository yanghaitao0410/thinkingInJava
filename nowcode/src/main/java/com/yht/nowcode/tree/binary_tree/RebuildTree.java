package com.yht.nowcode.tree.binary_tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据二叉树的前序遍历和中序遍历的结果，重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 *  preorder = [3,9,20,15,7]
 *  inorder =  [9,3,15,20,7]
 * @author yht
 * @create 2018/12/3
 */
public class RebuildTree {
    //存储中序遍历中值对应的索引 key inOrderValue   value index
    private static Map<Integer, Integer> indexForInOrders = new HashMap<>();

    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        for(int i = 0; i < in.length; i++) {
            indexForInOrders.put(in[i], i);
        }

        return reConstructBinaryTree(pre, 0, pre.length - 1, 0);
    }

    /**
     *
     * 前序遍历的第一个值为根节点的值，使用这个值将中序遍历结果分成两部分，左部分为树的左子树中序遍历结果，右部分为树的右子树中序遍历的结果。
     * @param pre 前序遍历数组
     * @param preBegin  开始位置
     * @param preEnd 结束位置
     * @param inBegin 中序遍历开始的位置
     * @return
     */
    private static TreeNode reConstructBinaryTree(int[] pre, int preBegin, int preEnd, int inBegin) {
        if(preBegin > preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(pre[preBegin]);
        int inIndex = indexForInOrders.get(root.val);
        int leftChildernSize = inIndex - inBegin;
        root.left = reConstructBinaryTree(
                pre, preBegin+ 1, preBegin + leftChildernSize, inBegin);
        root.right =
                reConstructBinaryTree(pre, preBegin + leftChildernSize + 1,
                        preEnd, inBegin + leftChildernSize + 1);
        return root;
    }


}
