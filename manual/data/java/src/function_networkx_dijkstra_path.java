package com.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import java.util.List;

public class NetworkXExample {
    public String compute_shortest_path(String start, String end, String u, String v, float weight,
                                        String x, String y, float weight2) {
        class NetworkXExample {
            public String calculatePath(String start, String end, String u, String v, float weight,
                                        String x, String y, float weight2) {
                Graph<String, DefaultWeightedEdge> graph =
                        new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

                // Add vertices
                graph.addVertex(u);
                graph.addVertex(v);
                DefaultWeightedEdge e1 = graph.addEdge(u, v);
                graph.setEdgeWeight(e1, weight);

                if (x != null && y != null) {
                    graph.addVertex(x);
                    graph.addVertex(y);
                    DefaultWeightedEdge e2 = graph.addEdge(x, y);
                    graph.setEdgeWeight(e2, weight2);
                }

                try {
                    DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra =
                            new DijkstraShortestPath<>(graph);
                    GraphPath<String, DefaultWeightedEdge> path =
                            dijkstra.getPath(start, end);

                    if (path == null) {
                        return "no path";
                    }

                    List<String> vertices = path.getVertexList();
                    return String.join(" -> ", vertices);
                } catch (IllegalArgumentException e) {
                    return "no path";
                }
            }
        }

        NetworkXExample example = new NetworkXExample();
        return example.calculatePath(start, end, u, v, weight, x, y, weight2);
    }
}