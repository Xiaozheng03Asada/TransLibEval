#include <string>
#include <vector>
#include <map>
#include <stdexcept>
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/dijkstra_shortest_paths.hpp>

using Graph = boost::adjacency_list<boost::vecS, boost::vecS, boost::undirectedS, boost::no_property, boost::property<boost::edge_weight_t, float>>;
using Vertex = boost::graph_traits<Graph>::vertex_descriptor;
using Edge = std::pair<Vertex, Vertex>;

class NetworkXExample {
public:
    std::string compute_shortest_path(const std::string& start, const std::string& end, const std::string& u, const std::string& v, float weight, const std::string& x, const std::string& y, float weight2) {
        Graph graph;
        std::map<std::string, Vertex> vertexMap;

        auto getVertex = [&](const std::string& name) {
            if (vertexMap.find(name) == vertexMap.end()) {
                vertexMap[name] = boost::add_vertex(graph);
            }
            return vertexMap[name];
        };

        Vertex uVertex = getVertex(u);
        Vertex vVertex = getVertex(v);
        boost::add_edge(uVertex, vVertex, weight, graph);

        if (!x.empty() && !y.empty()) {
            Vertex xVertex = getVertex(x);
            Vertex yVertex = getVertex(y);
            boost::add_edge(xVertex, yVertex, weight2, graph);
        }

        Vertex startVertex = getVertex(start);
        Vertex endVertex = getVertex(end);

        std::vector<Vertex> predecessors(boost::num_vertices(graph));
        std::vector<float> distances(boost::num_vertices(graph));

        boost::dijkstra_shortest_paths(graph, startVertex, boost::predecessor_map(&predecessors[0]).distance_map(&distances[0]));

        if (distances[endVertex] == std::numeric_limits<float>::max()) {
            return "no path";
        }

        std::vector<std::string> path;
        for (Vertex current = endVertex; current != startVertex; current = predecessors[current]) {
            path.push_back(std::to_string(current));
        }
        path.push_back(std::to_string(startVertex));

        std::reverse(path.begin(), path.end());

        std::string result;
        for (size_t i = 0; i < path.size(); ++i) {
            result += path[i];
            if (i != path.size() - 1) {
                result += " -> ";
            }
        }

        return result;
    }
};