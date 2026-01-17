package com.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class NetworkXExample {

    
    public float compute_minimum_spanning_tree(String u, String v, float weight, String x, String y, Float weight2) {
        
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        {
            
            graph.addVertex(u);
            graph.addVertex(v);
            
            DefaultWeightedEdge e1 = graph.addEdge(u, v);
            graph.setEdgeWeight(e1, weight);

            
            if (x != null && !x.isEmpty() && y != null && !y.isEmpty() && weight2 != null && weight2 != 0.0f) {
                graph.addVertex(x);
                graph.addVertex(y);
                DefaultWeightedEdge e2 = graph.addEdge(x, y);
                graph.setEdgeWeight(e2, weight2);
            }

            
            
            KruskalMinimumSpanningTree<String, DefaultWeightedEdge> mstAlgo = new KruskalMinimumSpanningTree<>(graph);
            double totalWeight = mstAlgo.getSpanningTree().getWeight();
            return (float) totalWeight;
        }
    }
}