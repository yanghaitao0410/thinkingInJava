package com.yht.leetcode.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author water
 * @date 2020/8/24
 **/
public class FindTarget {

    @Test
    public void test() {
        TreeNode node = new TreeNode(5);
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(6);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(7);

        node.left = node1;
        node.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.right = node5;

        System.out.println(findTarget(node, 9));
    }

    boolean result = false;

    public boolean findTarget(TreeNode root, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        mid(root, map, k);
        return result;
    }

    void mid(TreeNode root, Map<Integer, Integer> map, int k) {
        if(root == null) {
            return;
        }

        mid(root.left, map, k);

        int nextVal = k - root.val; //当前节点要是目标2个节点之一，另一个节点的值必须是val
        if(map.get(root.val) == null) { //从map中找当前节点的key是否已经有值了
            map.put(nextVal, root.val); //没有值将nextVal做为key，当前节点作为val 去下面的递归中找
        } else { //有值说明另一个节点在之前设置过 说明匹配上了
            result = true;
        }

        mid(root.right, map, k);
    }

}
