package com.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class NetworkXExample {

    // 所有功能代码均整合在该方法的括号内
    public float compute_minimum_spanning_tree(String u, String v, float weight, String x, String y, Float weight2) {
        // 创建无向加权图
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        {
            // 添加 u 和 v 两个顶点
            graph.addVertex(u);
            graph.addVertex(v);
            // 为 u-v 添加边，并设置权重
            DefaultWeightedEdge e1 = graph.addEdge(u, v);
            graph.setEdgeWeight(e1, weight);

            // 如果 x, y, weight2 均不为空且 weight2 不为 0，则添加额外的一条边
            if (x != null && !x.isEmpty() && y != null && !y.isEmpty() && weight2 != null && weight2 != 0.0f) {
                graph.addVertex(x);
                graph.addVertex(y);
                DefaultWeightedEdge e2 = graph.addEdge(x, y);
                graph.setEdgeWeight(e2, weight2);
            }

            // 计算最小生成树的总权重
            // 若无法解析 getSpanningTreeTotalWeight 方法，请使用 getSpanningTree().getWeight() 方法
            KruskalMinimumSpanningTree<String, DefaultWeightedEdge> mstAlgo = new KruskalMinimumSpanningTree<>(graph);
            double totalWeight = mstAlgo.getSpanningTree().getWeight();
            return (float) totalWeight;
        }
    }
}