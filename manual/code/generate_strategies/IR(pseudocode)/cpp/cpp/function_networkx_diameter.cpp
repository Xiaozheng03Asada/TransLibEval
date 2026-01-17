#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/johnson_all_pairs_shortest.hpp>
#include <boost/graph/connected_components.hpp>
#include <string>
#include <vector>
#include <sstream>
#include <limits>

class GraphUtils {
public:
    template<typename T>
    std::string calculate_diameter(const T& edges_str) {
        if constexpr (!std::is_convertible_v<T, std::string>) {
            return "failed";
        }

        std::string input = std::string(edges_str);
        if (input.empty()) {
            return "failed";
        }

        
        using EdgeProperty = boost::property<boost::edge_weight_t, int>;
        using Graph = boost::adjacency_list<boost::vecS, boost::vecS, 
                                          boost::undirectedS,
                                          boost::no_property,
                                          EdgeProperty>;
        Graph g;

        
        std::istringstream iss(input);
        std::string edge;
        while (std::getline(iss, edge, ',')) {
            size_t pos = edge.find('-');
            if (pos != std::string::npos) {
                std::string node1 = edge.substr(0, pos);
                std::string node2 = edge.substr(pos + 1);
                
                
                node1.erase(0, node1.find_first_not_of(" "));
                node1.erase(node1.find_last_not_of(" ") + 1);
                node2.erase(0, node2.find_first_not_of(" "));
                node2.erase(node2.find_last_not_of(" ") + 1);

                
                add_edge(
                    boost::vertex(node1[0] - 'A', g),
                    boost::vertex(node2[0] - 'A', g),
                    1, g
                );
            }
        }

        if (num_vertices(g) == 0) {
            return "failed";
        }

        
        std::vector<int> component(num_vertices(g));
        int num = boost::connected_components(g, &component[0]);

        if (num == 1) {  
            
            std::vector<std::vector<int>> distances(num_vertices(g),
                std::vector<int>(num_vertices(g)));
            
            
            bool has_path = johnson_all_pairs_shortest_paths(g, distances);
            if (!has_path) {
                return "failed";
            }

            
            int diameter = 0;
            for (const auto& row : distances) {
                for (int dist : row) {
                    if (dist != std::numeric_limits<int>::max()) {
                        diameter = std::max(diameter, dist);
                    }
                }
            }

            return "success: " + std::to_string(diameter);
        }

        return "failed";
    }
};