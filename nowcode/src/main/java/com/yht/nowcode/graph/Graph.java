package com.yht.nowcode.graph;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {

    //图中点的集合  key 节点编号  nodeNumber  节点
    public HashMap<Integer, GraphNode> nodes;

    //图中边的集合
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
