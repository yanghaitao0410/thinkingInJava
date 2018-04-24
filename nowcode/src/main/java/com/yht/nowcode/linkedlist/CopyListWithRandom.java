package com.yht.nowcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制含有随机指针的链表
 * 有一种特殊的链表节点类RandomNode，请深度拷贝给定链表
 *
 * 思路：准备一个Map，遍历整个链表，每遍历一个节点都创建一个新的节点，构造方法传入当前节点的值
 *      然后将当前节点作为key， 拷贝节点作为value存入map中
 *      然后再次遍历链表，当前节点的next通过map.get(key)获取到对应的拷贝节点，让当前节点map.get(key)获得的节点指向它
 *      然后更新到map中，random节点类似
 *      最后返回map.get(原链表根节点)就可以了
 */
public class CopyListWithRandom {

    public static RandomNode copyRandomList(RandomNode head) {
        Map<RandomNode, RandomNode> nodeMap = new HashMap<>();
        RandomNode curNode = head;
        while (curNode != null) {
            RandomNode newNode = new RandomNode(curNode.value);
            nodeMap.put(curNode, newNode);
            curNode = curNode.next;
        }
        curNode = head;

        while(curNode != null) {
            RandomNode newNode = nodeMap.get(curNode);
            newNode.next = nodeMap.get(curNode.next);
            newNode.random = nodeMap.get(curNode.random);
            nodeMap.put(curNode, newNode);
            curNode = curNode.next;
        }

        return nodeMap.get(head);
    }
}










