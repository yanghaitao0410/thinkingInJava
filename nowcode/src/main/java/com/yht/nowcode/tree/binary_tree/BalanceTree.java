package com.yht.nowcode.tree.binary_tree;

/**
 * 判断某一个二叉树是否是平衡二叉树
 * 使用递归实现
 * 思考每个节点应该满足的条件：
 *      每棵子数平衡，那么整颗二叉树就是平衡二叉树
 *      如何判断？应该返回的结果中有当前子数是否平衡，子数的高度（只有平衡时才有用）
 */
public class BalanceTree {
    public static class ReturnData{
        boolean isBalance;
        int height;
        public ReturnData(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }

    public static boolean isBalanceTree(TreeNode node) {
        return process(node).isBalance;
    }

    /**
     * 递归处理函数
     * @param node 子数头节点
     * @return
     */
    private static ReturnData process(TreeNode node) {
        //若当前节点为空，是平衡的、高度为0
        if(node == null) {
            return new ReturnData(true, 0);
        }
        //获取当前节点左子数返回结果
        ReturnData leftReturnData = process(node.left);
        //若左子数不是平衡的，直接返回false
        if(leftReturnData.isBalance == false) {
            return new ReturnData(false, 0);
        }
        ReturnData rightReturnData = process(node.right);
        if(rightReturnData.isBalance == false) {
            return new ReturnData(false, 0);
        }
        //程序走到这里表示左右子树都是平衡的，判断当前节点是不是平衡的
        if(Math.abs(leftReturnData.height - rightReturnData.height) > 1) {
            return new ReturnData(false, 0);
        }
        //当前节点的高度为子树较高的高度+1
        return  new ReturnData(true, Math.max(leftReturnData.height, rightReturnData.height) + 1);
    }
}
