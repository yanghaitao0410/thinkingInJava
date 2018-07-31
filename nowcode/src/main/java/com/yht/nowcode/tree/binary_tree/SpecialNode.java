package com.yht.nowcode.tree.binary_tree;

/**
 * 该二叉树节点比普通二叉树节点结构多了一个指向父节点的parent指针
 */
public class SpecialNode {

    public int value;
    public SpecialNode left;
    public SpecialNode right;
    public SpecialNode parent;

    public SpecialNode(int value) {
        this.value = value;
    }
}
