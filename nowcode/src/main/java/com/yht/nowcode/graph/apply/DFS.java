package com.yht.nowcode.graph.apply;

import com.yht.nowcode.graph.GraphNode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 图的深度优先遍历
 */
public class DFS {

    public static void dfs(GraphNode node) {
        if(node == null) {
            return;
        }
        Stack<GraphNode> stack = new Stack<>();
        Set<GraphNode> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        System.out.println(node.nodeNumber);
        while(!stack.isEmpty()) {
            GraphNode cur = stack.pop();
            //遍历当前节点的邻居节点
            for(GraphNode next : cur.nexts) {
                /**
                 * 如果发现一个没有遍历的节点，
                 * 表示发现一条新的路径，需要将该路径遍历完
                 * 再遍历当前节点的其他邻居节点
                 */
                if(!set.contains(next)) {
                    System.out.println(next.nodeNumber);
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    break;
                }
            }
        }
    }
}
