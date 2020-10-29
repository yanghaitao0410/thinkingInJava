package com.yht.leetcode.tree;

/**
 * 156：
 * 给定一个二叉树，其中所有的右节点要么是具有兄弟节点（拥有相同父节点的左节点）的叶节点，要么为空
 *  将此二叉树上下翻转并将它变成一棵树， 原来的右节点将转换成左叶节点。返回新的根。
 *
 *  例子:
 * 输入: [1,2,3,4,5]
 *
 *     1
 *    / \
 *   2   3
 *  / \
 * 4   5
 *
 * 输出: 返回二叉树的根 [4,5,2,#,#,3,1]
 *    4
 *   / \
 *  5   2
 *     / \
 *    3   1
 *
 * 说明:
 * 对 [4,5,2,#,#,3,1] 感到困惑? 下面详细介绍请查看 二叉树是如何被序列化的。
 *
 * 二叉树的序列化遵循层次遍历规则，当没有节点存在时，'#' 表示路径终止符。
 *
 * 这里有一个例子:
 *    1
 *   / \
 *  2   3
 *     /
 *    4
 *     \
 *      5
 * 上面的二叉树则被序列化为 [1,2,3,#,#,4,#,#,5].
 *
 * @author yht
 * @create 2020/7/19
 */
public class UpsideDownBinaryTree {

    /**
     * 先从最简单的情况开始分析：如果只有一个节点，那么返回该节点就可以了；
     * 如果存在三个节点，父节点和左右子节点（如果有右节点，至少会有一个左节点），
     * 那么新的树的结构是左节点变为父节点，右节点变为新的父节点的左节点，原来的父节点变为现在的右节点；
     *
     * 更复杂的情况，如果左子树已经被翻转了，那么现在应该把父节点和右节点放在哪里？有点递归的味道了；
     * 从例子里可以看出，节点1和节点3需要作为节点2的子节点；而节点2是左子树翻转后的最右边的节点；
     * 所以只要找到翻转后的左子树的最右的子节点，并把当前父节点和右节点连接上去就可以了；
     *
     * @param root
     * @return
     */
    public TreeNode upsideDownBinaryTree (TreeNode root) {
        if(root == null ||(root.left == null && root.right == null)) {
            return root;
        }
        //通过递归返回 翻转后的左子树的根节点
        TreeNode left = upsideDownBinaryTree(root.left);

        //找到翻转后的左子树的最右叶节点
        TreeNode rightLeaf = left;
        while(rightLeaf.right != null) {
            rightLeaf = rightLeaf.right;
        }
        //右节点变为新的父节点的左节点，原来的父节点变为现在的右节点；
        rightLeaf.left = root.right;
        rightLeaf.right = root;

        //将根节点原来的左右子树和根节点断开
        root.right = null;
        root.left = null;

        return left;
    }

}
