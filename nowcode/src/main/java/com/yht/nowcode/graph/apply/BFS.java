package com.yht.nowcode.graph.apply;

import com.yht.nowcode.graph.GraphNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 图的宽度优先遍历（又叫广度优先遍历）
 *
 */
public class BFS {

    /**
     * 很多算法只需要使用图的部分结构就可以满足
     *
     * 思路：准备一个队列和一个Set
     *
     * 队列先进先出
     * set用来判断节点在之前是否已经遍历过
     * @param node
     */
    public static void bfs(GraphNode node) {
        if(node == null) {
            return;
        }

        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>();
        queue.add(node);  //一开始将图的开始节点放入队列和set集合中
        set.add(node);

        while (!queue.isEmpty()) {
            node = queue.poll(); //遍历到队列的出队节点
            System.out.println(node);

            for(GraphNode next : node.nexts) { //循环当前节点的下一节点
                if(!set.contains(next)) { //若没有添加过，入队
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
}
