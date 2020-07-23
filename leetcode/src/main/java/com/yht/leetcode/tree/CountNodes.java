package com.yht.leetcode.tree;

import org.junit.Test;

/**
 * @Desc
 * 给出一个完全二叉树，求出该树的节点个数。
 *
 * 说明：
 * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
 *
 * 示例:
 * 输入:
 *     1
 *    / \
 *   2   3
 *  / \  /
 * 4  5 6
 *
 * 输出: 6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-complete-tree-nodes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author water
 * @date 2020/7/22
 **/
public class CountNodes {


    @Test
    public void test() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;

        System.out.println(countNodes(node1));
    }

    /**
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int height = 1;
        TreeNode node = root;
        while(node.left != null) {
            node = node.left;
            height++;
        }

        //通过二分法找到最后一层的最后一个节点 最左是1   最右是2^(height - 1)
        int left = 1, right = (int) Math.pow(2, height - 1);
        while(left <= right) { //left最大和right相等 这种情况是最下排节点全都存在
            int mid = left + (right - left) / 2;
                boolean exist = existNode(mid, root, height);
                if(exist) {
                    left = mid + 1; //加一是为了防止mid计算出来和left一样  导致死循环
                } else {
                    right = mid - 1;
                }

        }
        //跳出循环之后left会比实际节点个数多1 right和节点个数相同
        return right + (int)Math.pow(2, height - 1) - 1;
    }

    /**
     * 判断某一个位置是否有元素
     * @param lastIndex 节点在最后一层的下标 从1开始
     * @param root 二叉树跟节点
     * @param height 二叉树高度
     * @return
     */
    private boolean existNode(int lastIndex, TreeNode root, int height) {
        int left  = 1, right = (int)Math.pow(2, height - 1);
        for(int i = 1; i < height; i++) {
            int mid = left + (right - left) / 2;
            if(lastIndex <= mid) {
                root = root.left;
                right = mid;
            } else {
                root = root.right;
                left = mid;
            }
        }

        return root != null;
    }

}
