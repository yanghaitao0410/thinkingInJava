package com.yht.nowcode.graph;

import java.util.ArrayList;

/**
 * 图的节点
 */
public class GraphNode {

    //节点编号
    public int value;
    //指向当前节点的边的数量（入度）
    public int in;
    //由当前节点指向其他节点的边的数量（出度）
    public int out;

    //当前节点的邻居节点集合
    public ArrayList<GraphNode> nexts;

    //当前节点作为from，边的集合
    public ArrayList<Edge> edges;

    public GraphNode(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "value=" + value +
                ", in=" + in +
                ", out=" + out +
                '}';
    }
}
