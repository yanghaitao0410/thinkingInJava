package com.yht.nowcode.graph;

/**
 * 给定二维数组graphArr，其中的一维数组arr长度为3，
 *      arr[0]表示边的权重（长度），arr[1]表示from节点编号，arr[2]表示to节点编号
 * 将二维数组生成图在内存中的结构，用类来表示
 * [
 *      [7, 1, 2],
 *      [5, 1, 3],
 *      [2, 2, 3]
 * ]
 */
public class GraphGenerator {

    public static Graph createGraph(int[][] graphArr) {

        Graph graph = new Graph();

        for(int i = 0; i < graphArr.length; i++) {
            Integer weight = graphArr[i][0];  //权重
            Integer from = graphArr[i][1]; //from
            Integer to = graphArr[i][2];  //to

            if(!graph.nodes.containsKey(from)) { //若图中点的集合没有该from节点
                graph.nodes.put(from, new GraphNode(from)); //创建图中新的节点
            }

            if(!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new GraphNode(to));
            }
            GraphNode fromNode = graph.nodes.get(from);
            GraphNode toNode = graph.nodes.get(to);

            Edge newEdge = new Edge(weight, fromNode, toNode); //创建一条边
            fromNode.nexts.add(toNode); //将结束节点添加到开始节点的nexts中

            fromNode.out++; //from节点 出度 增加
            toNode.in++; //to节点 入度 增加

            fromNode.edges.add(newEdge); //from节点增加边
            graph.edges.add(newEdge); //图中增加边
        }

        return graph;
    }



}
