#include <string>
#include <vector>
#include <map>
#include <set>
#include <algorithm>
#include <stdexcept>
#include <iostream>
#include <limits>
#include <queue>
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/floyd_warshall_shortest.hpp>
#include <boost/graph/connected_components.hpp>

using namespace boost;

typedef adjacency_list<vecS, vecS, undirectedS, no_property, property<edge_weight_t, double>> Graph;
typedef graph_traits<Graph>::vertex_descriptor Vertex;
typedef graph_traits<Graph>::edge_descriptor Edge;

class GraphUtils {
public:
    std::string calculate_diameter(const std::string& edges_str) {
        if (edges_str.empty()) {
            return "failed";
        }

        Graph graph;
        std::map<std::string, Vertex> vertex_map;
        std::vector<std::string> edges = split(edges_str, ',');

        for (const std::string& edge : edges) {
            size_t pos = edge.find('-');
            if (pos != std::string::npos) {
                std::string node1 = edge.substr(0, pos);
                std::string node2 = edge.substr(pos + 1);
                node1 = trim(node1);
                node2 = trim(node2);

                if (vertex_map.find(node1) == vertex_map.end()) {
                    vertex_map[node1] = add_vertex(graph);
                }
                if (vertex_map.find(node2) == vertex_map.end()) {
                    vertex_map[node2] = add_vertex(graph);
                }

                add_edge(vertex_map[node1], vertex_map[node2], graph);
            }
        }

        if (num_vertices(graph) == 0) {
            return "failed";
        }

        if (is_connected(graph)) {
            std::vector<std::vector<double>> distance_matrix(num_vertices(graph), std::vector<double>(num_vertices(graph), std::numeric_limits<double>::infinity()));
            floyd_warshall_all_pairs_shortest_paths(graph, distance_matrix);

            double maxDistance = 0;
            for (size_t i = 0; i < num_vertices(graph); ++i) {
                for (size_t j = 0; j < num_vertices(graph); ++j) {
                    if (i != j && distance_matrix[i][j] != std::numeric_limits<double>::infinity()) {
                        maxDistance = std::max(maxDistance, distance_matrix[i][j]);
                    }
                }
            }

            return "success: " + std::to_string(static_cast<int>(maxDistance));
        } else {
            return "failed";
        }
    }

private:
    std::vector<std::string> split(const std::string& s, char delimiter) {
        std::vector<std::string> tokens;
        std::string token;
        for (char ch : s) {
            if (ch == delimiter) {
                tokens.push_back(token);
                token.clear();
            } else {
                token += ch;
            }
        }
        tokens.push_back(token);
        return tokens;
    }

    std::string trim(const std::string& str) {
        size_t first = str.find_first_not_of(' ');
        if (first == std::string::npos) {
            return "";
        }
        size_t last = str.find_last_not_of(' ');
        return str.substr(first, last - first + 1);
    }

    bool is_connected(const Graph& graph) {
        std::vector<int> component(num_vertices(graph));
        int num_components = connected_components(graph, &component[0]);
        return num_components == 1;
    }
};