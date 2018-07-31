package com.yht.nowcode.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 并查集：
 * 逻辑概念：假设由N个元素，每个元素自成一个集合（数学概念）{1}, {2}...{N}
 *      并查集提供两个操作：
 *          isSameSet(A, B)  查两个元素是否属于同一个集合
 *          union(A, B)  将A集合的元素和B集合的元素合并成一个大集合
 */
public class UnionFind {

    private static class Node {
        int val;
        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    '}';
        }
    }

    private static class CheckSet{
        /**
         * 使用map的对应关系实现向上指的过程
         * fatherMap: key 表示当前节点  value 表示当前节点的父节点
         */
        private HashMap<Node, Node> fatherMap;
        /**
         * sizeMap 表示集合个数  里面存储的key若不是代表点，该条记录无效
         * key 表示某集合的代表点  value 表示该集合的个数
         */
        private HashMap<Node, Integer> sizeMap;

        /**
         * 并查集的构造方法
         * 要想使用并查集，首先需要将数据指定
         * @param dataList
         */
        public CheckSet(List<Node> dataList) {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            makeSets(dataList);
        }

        /**
         * 将数据变成单个集合的形式
         *  即：每一个节点的父节点为自己本身，集合的大小为1
         * @param dataList
         */
        private void makeSets(List<Node> dataList) {
            fatherMap.clear();
            sizeMap.clear();
            for(Node data : dataList) {
                fatherMap.put(data, data);
                sizeMap.put(data, 1);
            }
        }

        /**
         * 查找一个节点所在集合的代表点
         * 找到后将途中的节点的父节点都设置为代表点
         * @param node
         * @return 代表点
         */
        private Node findHead(Node node) {
            Node father = fatherMap.get(node);
            if(father != node) {
                father = findHead(father);
                fatherMap.put(node, father);
            }
            return father;
        }

        /**
         * 判断两个节点所在的集合是否是同一个集合
         * @param A 节点1
         * @param B 节点2
         * @return boolean true 表示是同一个集合  false 表示不是同一个集合
         */
        public boolean isSameSet(Node A, Node B) {

            if(fatherMap.get(A) == null || fatherMap.get(B) == null) {
                throw new RuntimeException("集合中没有此节点！！");
            }
            return findHead(A) == findHead(B);
        }

        /**
         * 合并两个节点所在的集合
         * @param A
         * @param B
         */
        public void union(Node A, Node B) {

            if(fatherMap.get(A) == null || fatherMap.get(B) == null) {
                throw new RuntimeException("集合中没有此节点！！");
            }

            Node heapA = findHead(A);
            Node heapB = findHead(B);

            if(heapA != heapB) {

                int sizeA = sizeMap.get(heapA);
                int sizeB = sizeMap.get(heapB);
                int totalSize = sizeA + sizeB;
                if(sizeA > sizeB) {
                    updateFatherMapAndSizeMap(heapA, heapB, totalSize);
                } else {
                    updateFatherMapAndSizeMap(heapB, heapA, totalSize);
                }
            }
        }

        /**
         * 更新map
         * @param bigHeap 较大集合
         * @param smallHeap 较小集合
         * @param totalSize  集合合并之后的大小
         */
        private void updateFatherMapAndSizeMap(Node bigHeap, Node smallHeap, int totalSize) {
            fatherMap.put(smallHeap, bigHeap);  //将小集合的代表节点挂到大集合代表节点上
            sizeMap.put(bigHeap, totalSize);  //更新大集合的size
        }
    }

    public static  void main(String[] args) {
        List<Node> nodes = new ArrayList<Node>();

        for(int i = 0; i < 10; i++) {
            nodes.add(new Node(i));
        }

        CheckSet checkSet = new CheckSet(nodes);
        checkSet.union(nodes.get(0), nodes.get(1));
        checkSet.union(nodes.get(1), nodes.get(2));
        checkSet.union(nodes.get(2), nodes.get(3));
        System.out.println("heap:" + checkSet.findHead(nodes.get(0)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(1)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(2)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(3)));

        checkSet.union(nodes.get(4), nodes.get(5));
        System.out.println("heap:" + checkSet.findHead(nodes.get(4)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(5)));

        System.out.println(checkSet.fatherMap);
        System.out.println(checkSet.sizeMap);
        System.out.println(checkSet.isSameSet(nodes.get(0), nodes.get(1)));
        System.out.println();
        checkSet.union(nodes.get(0), nodes.get(4));
        System.out.println(checkSet.fatherMap);
        System.out.println(checkSet.sizeMap);

        System.out.println("heap:" + checkSet.findHead(nodes.get(0)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(1)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(2)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(3)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(4)));
        System.out.println("heap:" + checkSet.findHead(nodes.get(5)));
    }

}
