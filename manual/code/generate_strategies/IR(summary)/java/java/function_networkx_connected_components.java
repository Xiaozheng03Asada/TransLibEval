package com.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GraphUtils {
    public String find_connected_components(int node_count, int edge_count, String edges) {
        class GraphUtils {
            public String process() {
                Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

                if (edges != null && !edges.trim().isEmpty()) {
                    String[] edgeList = edges.split(",");
                    for (String edge : edgeList) {
                        String[] nodes = edge.split("-");
                        String node1 = nodes[0].trim();
                        String node2 = nodes[1].trim();
                        graph.addVertex(node1);
                        graph.addVertex(node2);
                        graph.addEdge(node1, node2);
                    }
                }

                ConnectivityInspector<String, DefaultEdge> ci =
                        new ConnectivityInspector<>(graph);
                
                List<Set<String>> componentsList = ci.connectedSets();
                Set<Set<String>> components = new HashSet<>(componentsList);

                if (!components.isEmpty()) {
                    String sizes = components.stream()
                            .map(Set::size)
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    return String.format("success: %d components with sizes: %s",
                            components.size(), sizes);
                } else if (node_count > 0) {
                    String sizes = "1, ".repeat(node_count - 1) + "1";
                    return String.format("success: %d components with sizes: %s",
                            node_count, sizes);
                } else {
                    return "failed";
                }
            }
        }
        return new GraphUtils().process();
    }
}