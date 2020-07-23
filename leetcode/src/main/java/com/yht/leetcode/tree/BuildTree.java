package com.yht.leetcode.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author water
 * @date 2020/7/1
 **/
public class BuildTree {

    //key为节点值，value为对应数组的下标
    Map<Integer, Integer> inMap = new HashMap<>();

    /**
     * 通过先序遍历、后续遍历构造二叉树
     * 思路：
     *  对于任意一颗树而言，前序遍历的形式总是
     *      [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
     * 即根节点总是前序遍历中的第一个节点。
     *
     * 而中序遍历的形式总是
     *      [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
     * 只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目。
     * 由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，因此我们就可以对应到前序遍历的结果中，对上述形式中的所有左右括号进行定位。
     * 这样，我们就知道了左子树的前序遍历和中序遍历结果，以及右子树的前序遍历和中序遍历结果，我们就可以递归地对构造出左子树和右子树，再将这两颗子树连接到根节点的左右位置。
     *
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTreeFromPre_in(int[] preorder, int[] inorder) {
        if(preorder.length != inorder.length) {
            return null;
        }

        for(int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        return myBuildTreeFromPre_in(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /**
     *
     * @param preorder 先序遍历数组
     * @param inorder 中序遍历数组
     * @param preIndexLeft 先序遍历左边界
     * @param preIndexRight 先序遍历右边界
     * @param inIndexLeft 中序遍历左边界
     * @param inIndexRight 中序遍历右边界
     * @return
     */
    private TreeNode myBuildTreeFromPre_in(int[] preorder, int[] inorder, int preIndexLeft, int preIndexRight, int inIndexLeft, int inIndexRight) {
        //左边界大于右边界 说明没有节点 返回null
        if(preIndexLeft > preIndexRight || inIndexLeft > inIndexRight) {
            return null;
        }

        //先序遍历第一个元素就是根节点
        TreeNode root = new TreeNode(preorder[preIndexLeft]);

        //获取根节点在中序遍历中的位置
        int rootInIndex = inMap.get(root.val);

        //左子树个数=中序遍历中从根节点到最左位置元素个数
        int leftSize = rootInIndex - inIndexLeft;
        //通过左子树个数，确定左子树在先序遍历中的右边界
        int left_preIndexRight = preIndexLeft + leftSize;

        root.left = myBuildTreeFromPre_in(preorder, inorder, preIndexLeft + 1, left_preIndexRight, inIndexLeft, rootInIndex - 1);

        //先序遍历中 右子树左边界为左子树右边界+1
        root.right = myBuildTreeFromPre_in(preorder, inorder, left_preIndexRight + 1, preIndexRight, rootInIndex + 1, inIndexRight);

        return root;
    }


    /**
     * 通过中序、后续遍历 还原二叉树
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTreeFromIn_pos(int[] inorder, int[] postorder) {
        if(inorder.length != postorder.length) {
            return null;
        }

        for(int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        return myBuildTreeFromIn_pos(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }

    private TreeNode myBuildTreeFromIn_pos(int[] inorder, int[] postorder, int inLeft, int inRight, int posLeft, int posRight) {
        if(inLeft > inRight || posLeft > posRight) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[posRight]);
        int rootInIndex = inMap.get(root.val);
        int leftTreeSize = rootInIndex - inLeft;

        root.left = myBuildTreeFromIn_pos(inorder, postorder, inLeft, rootInIndex - 1, posLeft, posLeft + leftTreeSize - 1);
        root.right = myBuildTreeFromIn_pos(inorder, postorder, rootInIndex + 1, inRight, posLeft + leftTreeSize, posRight - 1);

        return root;
    }


}
