package com.yht.nowcode.graph.apply;

import com.yht.nowcode.graph.Graph;
import com.yht.nowcode.graph.GraphNode;

import java.util.*;

/**
 * 拓扑排序
 */
public class TopologySort {

    /**
     * 有向图并且无环
     *
     * 实现思路：借助一个map存储节点的入度，每排好一个节点后，将依赖当前节点的入度减1，
     * 创建一个队列存储入度为0的节点
     * 遍历当前队列
     * @param graph
     * @return
     */
    public static List<GraphNode> topologySort(Graph graph) {
        Map<GraphNode, Integer> inMap = new HashMap<>(); //存储节点的入度
        Queue<GraphNode> zeroInQueue = new LinkedList<>(); //存储入度为0的节点
        List<GraphNode> result = new ArrayList<>();

        //将图中所有的节点的入度放入map中
        for(GraphNode node : graph.nodes.values()) {
            inMap.put(node, node.in);
            //将入度为0的节点放入队列
            if(node.in == 0) {
                zeroInQueue.add(node);
            }
        }

        while (!zeroInQueue.isEmpty()) {
            //出对一个节点，放入result中
            GraphNode node = zeroInQueue.poll();
            result.add(node);
            //将当前节点的邻居节点的入度减1
            for(GraphNode next : node.nexts) {
                Integer inCount = inMap.get(next) - 1;
                inMap.put(next, inCount);
                //邻居界点的入度若为0，加入到队列
                if(inCount == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
