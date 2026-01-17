#include <iostream>
#include <vector>
#include <set>
#include <sstream>
#include <algorithm>
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/connected_components.hpp>

using namespace boost;

typedef adjacency_list<vecS, vecS, undirectedS> Graph;
typedef graph_traits<Graph>::vertex_descriptor Vertex;

class GraphUtils {
public:
    std::string find_connected_components(int node_count, int edge_count, const std::string& edges) {
        Graph graph(node_count);

        if (!edges.empty()) {
            std::istringstream iss(edges);
            std::string edge;
            while (std::getline(iss, edge, ',')) {
                size_t dash_pos = edge.find('-');
                std::string node1 = edge.substr(0, dash_pos);
                std::string node2 = edge.substr(dash_pos + 1);
                int u = std::stoi(node1) - 1;
                int v = std::stoi(node2) - 1;
                add_edge(u, v, graph);
            }
        }

        std::vector<int> component(num_vertices(graph));
        int num_components = connected_components(graph, &component[0]);

        if (num_components > 0) {
            std::vector<int> component_sizes(num_components, 0);
            for (size_t i = 0; i < component.size(); ++i) {
                component_sizes[component[i]]++;
            }

            std::ostringstream oss;
            for (size_t i = 0; i < component_sizes.size(); ++i) {
                oss << component_sizes[i];
                if (i < component_sizes.size() - 1) {
                    oss << ", ";
                }
            }

            return "success: " + std::to_string(num_components) + " components with sizes: " + oss.str();
        } else if (node_count > 0) {
            std::string sizes;
            for (int i = 0; i < node_count; ++i) {
                sizes += "1";
                if (i < node_count - 1) {
                    sizes += ", ";
                }
            }
            return "success: " + std::to_string(node_count) + " components with sizes: " + sizes;
        } else {
            return "failed";
        }
    }
};