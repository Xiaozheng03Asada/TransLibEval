#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/connected_components.hpp>
#include <string>
#include <vector>
#include <sstream>

class GraphUtils {
public:
    template<typename T>
    std::string find_connected_components(int node_count, int edge_count, const T& edges) {
        if constexpr (!std::is_convertible_v<T, std::string>) {
            return "failed";
        }

        using Graph = boost::adjacency_list<boost::vecS, boost::vecS, boost::undirectedS>;
        Graph g;

        std::string edges_str = std::string(edges);
        if (!edges_str.empty()) {
            std::istringstream iss(edges_str);
            std::string edge;
            while (std::getline(iss, edge, ',')) {
                
                edge.erase(0, edge.find_first_not_of(" "));
                edge.erase(edge.find_last_not_of(" ") + 1);

                
                size_t pos = edge.find("-");
                if (pos != std::string::npos) {
                    std::string node1 = edge.substr(0, pos);
                    std::string node2 = edge.substr(pos + 1);
                    
                    
                    add_edge(
                        boost::vertex(node1[0] - 'A', g),
                        boost::vertex(node2[0] - 'A', g),
                        g
                    );
                }
            }
        }

        
        std::vector<int> component(num_vertices(g));
        int num = boost::connected_components(g, &component[0]);

        if (!component.empty() || node_count > 0) {
            std::ostringstream result;
            result << "success: ";

            if (!component.empty()) {
                
                std::vector<int> sizes(num, 0);
                for (int c : component) {
                    sizes[c]++;
                }
                
                result << num << " components with sizes: ";
                for (size_t i = 0; i < sizes.size(); ++i) {
                    if (i > 0) result << ", ";
                    result << sizes[i];
                }
            } else {
                
                result << node_count << " components with sizes: ";
                for (int i = 0; i < node_count; ++i) {
                    if (i > 0) result << ", ";
                    result << "1";
                }
            }
            
            return result.str();
        }

        return "failed";
    }
};