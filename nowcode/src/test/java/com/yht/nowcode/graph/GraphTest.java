package com.yht.nowcode.graph;

import com.yht.nowcode.graph.apply.BFS;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphTest {
    private int[][] graphArr;
    Graph graph;

    @Before
    public void before() {
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

        graph = GraphGenerator.createGraph(graphArr);
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
}
