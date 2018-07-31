package com.yht.nowcode.tree.binary_tree;

/**
 * 在二叉树中找到一个节点的后继节点
 * 【题目】
 * 假设有一颗SpecialNode类型的节点组成的二叉树，树中每个节点的parent指针都正确的指向自己的父节点，
 * 头节点的parent指向null。只给一个在二叉树中的某个节点node,请实现返回node的后继节点的函数。
 * 注：在二叉树的中序遍历的序列中，node的下一个节点叫做node的后继节点
 */
public class DescendantNode {

    /**
     *                      1
     *           2                      3
     *      4        5              6        7
     *  中序遍历：4 2 5 1 6 3 7
     *      情况1：当前节点有右子树，后继节点为右子树最左节点，
     *          例如：2的后继节点为5
     *               1的后续节点为6
     *      情况2：一：当前节点无左子数，从当前节点向上找，
     *                直到找到父节点，满足当前节点包含在找到的父节点的左子树中
     *                  例如：5的后继节点为1
     *                       6的后继节点为3
     *             二：若找到根节点也没有找到，说明当前节点为二叉树的末尾，返回null
     */

    public static SpecialNode getDescendantNode(SpecialNode node) {

        if(node == null) {
            return null;
        }
        //情况1
        if(node.right != null) {
            return getLeftSpecialNode(node.right);
        } else {
            return getFirstParentNodeByLeft2(node);
        }
    }

    /**
     * 获取当前节点上面第一个满足：当前节点在找到的父节点的左子树中
     * 递归版
     * @return
     */
    private static SpecialNode getFirstParentNodeByLeft(SpecialNode node) {

        if(node == null) {
            return null;
        }
        SpecialNode parent = node.parent;
        if(parent != null && parent.left != null && parent.left == node) {
            return parent;
        } else {
            return getFirstParentNodeByLeft(parent);
        }
    }

    /**
     * 非递归版
     * @param node
     * @return
     */
    private static SpecialNode getFirstParentNodeByLeft2(SpecialNode node) {

        SpecialNode parent = node.parent;
        //如果当前节点不是parent节点的左节点，向上跳，继续判断
        while (parent != null && parent.left != node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    /**
     * 获取当前二叉树的最左节点
     * @param node
     * @return
     */
    private static SpecialNode getLeftSpecialNode(SpecialNode node) {

        if(node == null || node.left == null) {
            return node;
        }
        SpecialNode leftNode = null;
        while (node.left != null) {
            node = node.left;
            leftNode = node;
        }
        return leftNode;
    }

    public static void inTraverseRecursion(SpecialNode head) {
        if(head == null) {
            return;
        }
        inTraverseRecursion(head.left);
        System.out.printf("%d ",head.value);
        inTraverseRecursion(head.right);
    }

}
