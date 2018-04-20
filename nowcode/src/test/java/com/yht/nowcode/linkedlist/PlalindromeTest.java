package com.yht.nowcode.linkedlist;

import org.junit.Before;
import org.junit.Test;

public class PlalindromeTest {
    Node node;

    @Before
    public void before() {
        node = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(2);
//        Node node5 = new Node(1);
        node.next = node2;
        //node2.next = node4;
        node2.next = node3;
        node3.next = node4;
//        node4.next = node5;
    }

    @Test
    public void testPlalindrome1() {
        System.out.println(Plalindrome.plalindrome1(node));

    }

    @Test
    public void testPlalindrome2() {
        System.out.println(Plalindrome.plalindrome2(node));

    }

    @Test
    public void testPlalindrome3() {
        System.out.println(Plalindrome.plalindrome3(node));
        while(node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }

    }

}
