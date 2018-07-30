package com.yht.nowcode.binary_tree;

/**
 * 折纸问题：
 *  【题目】
 *  请把一段纸条竖着放在桌子上，然后从纸条的下方向上方对折一次，压出折痕后展开。
 *  此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。如果从纸条的下方向上方连续对折两次，
 *  压出折痕后展开，此时有3条折痕，从上到下依次是下折痕、下折痕和上折痕。
 *  给定一个输入参数N,代表纸条都从下边向上方连续对折N次，请从上到下打印所有折痕的方向
 */
public class PaperFolding {

    /**
     * 思路：(基本解法)
     *      折一次                  下
     *      折两次         下                  上
     *      折三次     下      上         下         上
     *      折四次  下   上  下  上     下   上    下  上
     * 发现折出的折痕方向从上到下可以组成一个二叉树:
     * 该二叉树的头节点为下折痕，左子树的头节点为下折痕，右子树的头节点为上折痕，再生产子节点的左为下折痕，右为上折痕
     *依次打印折痕实际为该二叉树的中序遍历
     * step1 : 根据传入的参数N构建N层二叉树
     * step2:中序遍历该二叉树
     */

    public static void printPaperFolds(int N) {

        TreeNode head  = buildPaperBinaryTree(N);
        inTraverseRecursion(head);
    }

    public static void inTraverseRecursion(TreeNode head) {

        if(head == null) {
            return;
        }
        inTraverseRecursion(head.left);
        System.out.printf("%s ",head.val == PaperFoldDirection.UP.ordinal() ? "up" : "down");
        inTraverseRecursion(head.right);
    }

    /**
     * 构建n层头节点为下折痕，左子节点为下折痕，右子节点为上折痕的二叉树
     * @param N
     * @return
     */
    private static TreeNode buildPaperBinaryTree(int N) {
        TreeNode head = new TreeNode(PaperFoldDirection.DOWN.ordinal());
        N--;
        buildPaperBinaryTreeChild(head, N);
        return head;
    }

    private static void buildPaperBinaryTreeChild(TreeNode head, int N) {

        if(N < 1) {
            return;
        }
        head.left = new TreeNode(PaperFoldDirection.DOWN.ordinal());
        head.right = new TreeNode(PaperFoldDirection.UP.ordinal());
        buildPaperBinaryTreeChild(head.left, --N);
        buildPaperBinaryTreeChild(head.right, N);
    }

    /**
     * 优解方案：
     * 不真正构建二叉树，仅仅中序遍历一次
     * 通过boolean区分上下折痕打印
     * true : 下折痕
     * false : 上折痕
     * @param N 一共打印的层数
     */
    public static void printPaperFolds2(int N) {
        //将头节点传入
        printProcess(1, N, true);
    }

    /**
     * 打印当前节点
     * @param i 当前层是第几层
     * @param N 一共打印的层数
     * @param down  当前节点是否是下折痕
     */
    private static void printProcess(int i, int N, boolean down) {

        if(i > N) {
            return;
        }
        //step1:打印当前节点的左子树
        printProcess(i + 1, N, true);
        //step2:打印当前节点
        System.out.printf("%s ", down == true ? "down" : "up");
        //step3:打印当前节点的右子数
        printProcess(i + 1, N, false);
    }
    /**
     * 如何想到的：
     *  提醒1：折N次，产生2^n - 1 条折痕
     *  提醒2：具体折，分析每次折痕的生成
     *  需要对二叉树的基础很熟
     */

}
