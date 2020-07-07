package com.yht.nowcode.stack_queue;

import com.yht.nowcode.tree.binary_tree.TreeNode;

import java.util.HashMap;
import java.util.Stack;

/**
 * 一个数组的MaxTree定义如下：
 *  数组必须没有重复元素
 *  MaxTree是一颗二叉树，数组的每一个值对应二叉树的一个节点
 *  包括MaxTree树在内且在其中的每一颗子树上，值最大的节点都是树的头
 *
 * 给定一个没有重复元素的数组arr，写出生成这个数组的maxTree的函数
 * @author yht
 * @create 2020/6/15
 */
public class MaxTree {

    public TreeNode getMaxTree(int [] arr) {
        TreeNode[] nArr = new TreeNode[arr.length];
        for(int i = 0; i < arr.length; i++) {
            nArr[i] = new TreeNode(arr[i]);
        }

        //该栈从栈底到栈顶：由大到小排列
        Stack<TreeNode> stack = new Stack<>();
        HashMap<TreeNode, TreeNode> lBigMap = new HashMap<>();
        HashMap<TreeNode, TreeNode> rBigMap = new HashMap<>();

        //设置左边第一个比当前数大map
        for(int i = 0; i < nArr.length; i++) {
            TreeNode curNode = nArr[i];
            //遍历过程中发现当前数比栈顶元素大，违背从栈低到栈顶，由大到小原则，需要先将栈顶元素处理
            while ((!stack.isEmpty()) && stack.peek().val < curNode.val) {
                popStackSetMap(stack, lBigMap);
            }
            stack.push(curNode);
        }
        //处理栈中剩下的元素
        while(!stack.isEmpty()) {
            popStackSetMap(stack, lBigMap);
        }

        //设置右边第一个比当前数大map
        for(int i = nArr.length - 1; i >=0; i--) {
            TreeNode curNode = nArr[i];
            //遍历过程中发现当前数比栈顶元素大，违背从栈低到栈顶，由大到小原则，需要先将栈顶元素处理
            while ((!stack.isEmpty()) && stack.peek().val < curNode.val) {
                popStackSetMap(stack, rBigMap);
            }
            stack.push(curNode);
        }
        //处理栈中剩下的元素
        while(!stack.isEmpty()) {
            popStackSetMap(stack, rBigMap);
        }

        TreeNode head = null;
        for(int i = 0; i < nArr.length; i++) {
            TreeNode curNode = nArr[i];
            TreeNode leftBig = lBigMap.get(curNode);
            TreeNode rightBig = rBigMap.get(curNode);
            //当前数是最大的数 它就是根节点
            if(leftBig == null && rightBig == null) {
                head = curNode;
            } else if(leftBig == null) {
                //当前数左边没有更大的数，只需要考虑将当前数放到右边比它大数下面就好了
                //先放左子树
                if(leftBig.left == null) {
                    rightBig.left = curNode;
                } else {
                    rightBig.right = curNode;
                }
            } else if(rightBig == null) {
                if(leftBig.left == null) {
                    leftBig.left = curNode;
                } else {
                    leftBig.right = curNode;
                }
            } else { //两者都不为null，将当前数挂在较小数下面
                TreeNode parent = leftBig.val < rightBig.val ? leftBig : rightBig;
                if(parent.left == null) {
                    parent.left = curNode;
                } else {
                    parent.right = curNode;
                }
            }
        }

        return head;
    }

    /**
     * 将栈顶元素和第一个比该元素大的数设置到map中
     * 因为栈低到栈顶，由大到小原则 所以栈顶元素的下一个元素就是第一个比当前元素大的了
     * @param stack
     * @param map
     */
    public void popStackSetMap(Stack<TreeNode> stack, HashMap<TreeNode, TreeNode> map) {
        TreeNode popNode = stack.pop();
        if(stack.isEmpty()) {
            map.put(popNode, null);
        } else {
            map.put(popNode, stack.peek());
        }
    }


}
