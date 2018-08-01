package com.yht.nowcode.graph;

/**
 * 图的边
 */
public class Edge {

    //边的权重（距离）
    public int weight;

    //边的开始节点
    public GraphNode from;

    //边的结束节点
    public GraphNode to;

    public Edge(int weight, GraphNode from, GraphNode to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
