package com.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;

public class GraphUtils {
    public String calculate_diameter(String edges_str) {
        class GraphUtils {
            public String calculate_diameter(String edges_str) {
                if (edges_str == null || edges_str.isEmpty()) {
                    return "failed";
                }

                Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
                String[] edges = edges_str.split(",");

                for (String edge : edges) {
                    if (edge.contains("-")) {
                        String[] nodes = edge.split("-");
                        String node1 = nodes[0].trim();
                        String node2 = nodes[1].trim();
                        graph.addVertex(node1);
                        graph.addVertex(node2);
                        graph.addEdge(node1, node2);
                    }
                }

                if (graph.vertexSet().isEmpty()) {
                    return "failed";
                }

                ConnectivityInspector<String, DefaultEdge> connectivityInspector =
                        new ConnectivityInspector<>(graph);

                if (connectivityInspector.isConnected()) {
                    FloydWarshallShortestPaths<String, DefaultEdge> floyd =
                            new FloydWarshallShortestPaths<>(graph);

                    
                    double maxDistance = 0;
                    for (String v1 : graph.vertexSet()) {
                        for (String v2 : graph.vertexSet()) {
                            if (!v1.equals(v2)) {
                                double distance = floyd.getPath(v1, v2).getLength();
                                maxDistance = Math.max(maxDistance, distance);
                            }
                        }
                    }

                    return "success: " + (int)maxDistance;
                } else {
                    return "failed";
                }
            }
        }
        return new GraphUtils().calculate_diameter(edges_str);
    }
}