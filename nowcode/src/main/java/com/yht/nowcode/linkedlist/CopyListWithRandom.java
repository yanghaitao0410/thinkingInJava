package com.yht.nowcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制含有随机指针的链表
 * 有一种特殊的链表节点类RandomNode，请深度拷贝给定链表
 *
 */
public class CopyListWithRandom {

    /**
     *  思路1：准备一个Map，遍历整个链表，每遍历一个节点都创建一个新的节点，构造方法传入当前节点的值
     *       然后将当前节点作为key， 拷贝节点作为value存入map中
     *       然后再次遍历链表，当前节点的next通过map.get(key)获取到对应的拷贝节点，让当前节点map.get(key)获得的节点指向它
     *       然后更新到map中，random节点类似
     *      最后返回map.get(原链表根节点)就可以了
     * @param head
     * @return
     */
    public static RandomNode copyRandomList1(RandomNode head) {
        Map<RandomNode, RandomNode> nodeMap = new HashMap<>();
        RandomNode curNode = head;
        while (curNode != null) {
            RandomNode newNode = new RandomNode(curNode.value);
            nodeMap.put(curNode, newNode);
            curNode = curNode.next;
        }
        curNode = head;

        while(curNode != null) {
            /*RandomNode newNode = nodeMap.get(curNode);
            newNode.next = nodeMap.get(curNode.next);
            newNode.random = nodeMap.get(curNode.random);
            nodeMap.put(curNode, newNode);
            curNode = curNode.next;*/

            nodeMap.get(curNode).next = nodeMap.get(curNode.next);
            nodeMap.get(curNode).random = nodeMap.get(curNode.random);
            curNode = curNode.next;
        }

        return nodeMap.get(head);
    }

    /**
     * 思路2：不使用map，遍历每一个节点的时候将当前节点的next指向拷贝节点，拷贝节点的下一个指向当前节点的原next
     * 遍历结束后奇数位置是原节点，偶数位置是拷贝节点，
     * 将原节点和拷贝节点分为一组，依次遍历，原链表节点的random指针指向的节点是不变的，
     * 拷贝节点的random节点即为原链表random节点的next
     * 遍历结束后将两个链表拆开，返回拷贝链表头节点
     * @param head
     * @return
     */
    public static RandomNode copyRandomList2(RandomNode head) {
        RandomNode curNode = head;
        while (curNode != null) { //原链表和拷贝链表交替形成新链表
            RandomNode newNode = new RandomNode(curNode.value);
            newNode.next = curNode.next;
            curNode.next = newNode;

            curNode = newNode.next;
        }

        curNode = head;
        while(curNode != null) { //设置拷贝节点的random节点
            RandomNode copyNode = curNode.next;
            if(curNode.random != null) {
                copyNode.random = curNode.random.next;
            }else {
                copyNode.random = null;
            }
            curNode = curNode.next.next;
        }

        RandomNode originCur = head;
        RandomNode copyHead = head.next;
        RandomNode copyCur = copyHead;
        curNode = head.next.next;

        while(curNode != null) { //将原链表和拷贝链表拆开，设置next节点
            RandomNode tmp = curNode.next.next;
            originCur.next = curNode;
            copyCur.next = curNode.next;

            originCur = originCur.next;
            copyCur = copyCur.next;
            curNode = tmp;
            if(curNode == null) {
                originCur.next = null;
                copyCur.next = null;
            }
        }


        return copyHead;
    }
}










