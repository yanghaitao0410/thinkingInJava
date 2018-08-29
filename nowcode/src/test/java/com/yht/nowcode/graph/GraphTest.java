package com.yht.nowcode.graph;

import com.yht.nowcode.graph.apply.BFS;
import com.yht.nowcode.graph.apply.DFS;
import com.yht.nowcode.graph.apply.MinimumSpanningTree;
import com.yht.nowcode.graph.apply.TopologySort;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphTest {
    private int[][] graphArr;
    private int[][] graphArr2; //无向图数组
    Graph graph; //有向图
    Graph graph2; //无向图

    @Before
    public void before() {
        // 0位置表示权重， 1位置表示from点 ，2位置表示to点
        graphArr = new int[][]{
                {1, 1, 2},
                {1, 1, 3},
                {1, 1, 4},
                {1, 2, 3},
                {1, 2, 7},
                {1, 7, 3},
                {1, 3, 5},
                {1, 4, 6}
        };

        graphArr2 = new int[][]{
                {1, 1, 2},
                {1, 1, 3},
                {1, 1, 4},
                {1, 2, 3},
                {1, 2, 7},
                {1, 7, 3},
                {1, 3, 5},
                {1, 4, 6},
                {1, 2, 1},
                {1, 3, 1},
                {1, 4, 1},
                {1, 3, 2},
                {1, 7, 2},
                {1, 3, 7},
                {1, 5, 3},
                {1, 6, 4}
        };

        graph = GraphGenerator.createGraph(graphArr);
        graph2 = GraphGenerator.createGraph(graphArr2);
    }

    @Test
    public void test() {

        List<Edge> edges = new ArrayList<>();
        GraphNode from = new GraphNode(1);
        GraphNode to = new GraphNode(2);
        Edge edge = new Edge(7, from, to);
        System.out.println(edge);
        from.nexts.add(to);
        from.out++;
        to.in++;
        System.out.println(edge);
    }

    @Test
    public void testBfs() {

        GraphNode node = graph.nodes.get(1);

        BFS.bfs(node);
    }

    @Test
    public void testDfs() {
        GraphNode node = graph.nodes.get(1);

        DFS.dfs(node);
    }

    @Test
    public void testTopologySort() {
        List<GraphNode> graphNodes = TopologySort.topologySort(graph);
        for(GraphNode node : graphNodes) {
            System.out.println(node);
        }
    }

    @Test
    public void testKruskal() {
        Set<Edge> edges = MinimumSpanningTree.kruskalMST(graph2);
        for(Edge edge : edges) {
            System.out.println(edge);
        }
    }

    @Test
    public void testPrim() {
        Set<Edge> edges = MinimumSpanningTree.primMST(graph2);
        for(Edge edge : edges) {
            System.out.println(edge);
        }
    }
}
