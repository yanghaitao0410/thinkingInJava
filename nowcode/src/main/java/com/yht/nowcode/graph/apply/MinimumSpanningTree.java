package com.yht.nowcode.graph.apply;

import com.yht.nowcode.graph.Edge;
import com.yht.nowcode.graph.Graph;
import com.yht.nowcode.graph.GraphNode;

import java.util.*;

/**
 * 要求：无向图
 * 最小生成树
 *  最小生成树其实是最小权重生成树的简称。
 * 一个连通图可能有多个生成树。当图中的边具有权值时，总会有一个生成树的边的权值之和小于或者等于其它生成树的边的权值之和。
 * 广义上而言，对于非连通无向图来说，它的每一连通分量同样有最小生成树，它们的并被称为最小生成森林。
 * 以有线电视电缆的架设为例，若只能沿着街道布线，则以街道为边，而路口为顶点，其中必然有一最小生成树能使布线成本最低。
 *
 */
public class MinimumSpanningTree {


    private static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2) {
            if(o1.weight < o2.weight) {
                return -1;
            }else if(o1.weight > o2.weight) {
                return 1;
            }
            return 0;
        }
    }

    public static class UnionFind {
        private HashMap<GraphNode, GraphNode> fatherMap;
        private HashMap<GraphNode, Integer> rankMap;

        public UnionFind() {
            fatherMap = new HashMap<GraphNode, GraphNode>();
            rankMap = new HashMap<GraphNode, Integer>();
        }

        private GraphNode findFather(GraphNode n) {
            GraphNode father = fatherMap.get(n);
            if (father != n) {
                father = findFather(father);
            }
            fatherMap.put(n, father);
            return father;
        }

        public void makeSets(Collection<GraphNode> nodes) {
            fatherMap.clear();
            rankMap.clear();
            for (GraphNode node : nodes) {
                fatherMap.put(node, node);
                rankMap.put(node, 1);
            }
        }

        public boolean isSameSet(GraphNode a, GraphNode b) {
            return findFather(a) == findFather(b);
        }

        public void union(GraphNode a, GraphNode b) {
            if (a == null || b == null) {
                return;
            }
            GraphNode aFather = findFather(a);
            GraphNode bFather = findFather(b);
            if (aFather != bFather) {
                int aFrank = rankMap.get(aFather);
                int bFrank = rankMap.get(bFather);
                if (aFrank <= bFrank) {
                    fatherMap.put(aFather, bFather);
                    rankMap.put(bFather, aFrank + bFrank);
                } else {
                    fatherMap.put(bFather, aFather);
                    rankMap.put(aFather, aFrank + bFrank);
                }
            }
        }
    }

    /**
     * k算法
     * 通过判断图中边的权重的方法，
     * 遍历图中所有边：每次获取图中最小权重的边，判断若加上该边后是否形成回路，若不形成回路，加入最小生成树集合中，
     * 最后返回
     * @param graph
     * @return
     */
    public static Set<Edge> kruskalMST(Graph graph) {
        Set<Edge> resultSet = new HashSet<>();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(new EdgeComparator());
        edgeQueue.addAll(graph.edges);
        UnionFind unionFind = new UnionFind();
        //将图中每个节点单独成一个集合
        unionFind.makeSets(graph.nodes.values());
        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            /*
                若当前边的开始节点和结束节点不属于同一个集合，表示没有形成回路，
                加入到最小生成树集合中，并将这两个点合并为同一个集合
             */
            if(!unionFind.isSameSet(edge.from, edge.to)) {
                resultSet.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return resultSet;
    }

    /**
     * p算法：通过解锁点来解锁新的边
     * @param graph
     * @return
     */
    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> resultEdgeSet = new HashSet<>(); //返回的集合
        Set<GraphNode> unionNodeSet = new HashSet<>(); //节点是否加入集合的标记
        Set<Edge> unionEdgeSet = new HashSet<>(); //边是否加入集合的标记
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(new EdgeComparator());

        //将任意一个节点解锁
        for(GraphNode node : graph.nodes.values()) {
            unionNodeSet.add(node); //将该点加入到标记集合
            edgeQueue.addAll(node.edges); //将该节点的边加入优先队列
            unionEdgeSet.addAll(node.edges); //将边加入到标记集合中
            break;
        }
        //遍历所有的节点
        while (!edgeQueue.isEmpty()) {
            Edge minEdge = edgeQueue.poll(); //弹出最小的边
            GraphNode toNode = minEdge.to; //获取到最小边的to节点
            if(!unionNodeSet.contains(toNode)) { //若to节点是新的节点
                unionNodeSet.add(toNode); //将to节点加入到集合中
                for(Edge edge : toNode.edges) {
                    if(!unionEdgeSet.contains(edge)) { //向小根堆中添加新边
                        edgeQueue.add(edge);
                    }
                }
                resultEdgeSet.add(minEdge); //将边加入到最小生成树集合中
            }
        }
        return resultEdgeSet;
    }
}
