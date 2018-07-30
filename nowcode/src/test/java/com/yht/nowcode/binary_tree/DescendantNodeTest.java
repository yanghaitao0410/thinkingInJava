package com.yht.nowcode.binary_tree;

import org.junit.Before;
import org.junit.Test;

public class DescendantNodeTest {

    SpecialNode head;
    SpecialNode node1;
    SpecialNode node2;
    SpecialNode node3;
    SpecialNode node4;
    SpecialNode node5;
    SpecialNode node6;


    @Before
    public void before() {
        head = new SpecialNode(0);
        node1 = new SpecialNode(1);
        node2 = new SpecialNode(2);
        node3 = new SpecialNode(3);
        node4 = new SpecialNode(4);
        node5 = new SpecialNode(5);
        node6 = new SpecialNode(6);

        head.parent = null;
        head.left = node1;
        head.right = node2;

        node1.parent = head;
        node1.left = node3;
        node1.right = node4;

        node2.parent = head;
        node2.left = node5;
        node2.right = node6;

        node3.parent = node1;
        node4.parent = node1;

        node5.parent = node2;
        node6.parent = node2;
    }

    @Test
   public void testGetDescendantNode() {

        DescendantNode.inTraverseRecursion(head);
        System.out.println();
        SpecialNode node = DescendantNode.getDescendantNode(head);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node1);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node2);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node3);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node4);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node5);
        System.out.println(node == null ? "null" : node.value);

        node = DescendantNode.getDescendantNode(node6);
        System.out.println(node == null ? "null" : node.value);
   }

}
