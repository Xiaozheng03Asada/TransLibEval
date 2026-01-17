
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/kruskal_min_spanning_tree.hpp>
#include <string>

class NetworkXExample {
public:
    template<typename T1, typename T2, typename T3, typename T4>
    float compute_minimum_spanning_tree(const T1& u, const T2& v, float weight,
                                      const T3& x = T3(), const T4& y = T4(), 
                                      float weight2 = 0) {
        using EdgeProperty = boost::property<boost::edge_weight_t, float>;
        using Graph = boost::adjacency_list<boost::vecS, boost::vecS, 
                                          boost::undirectedS,
                                          boost::no_property,
                                          EdgeProperty>;
        Graph g;

        
        add_edge(0, 1, weight, g);

        
        if (!std::string(x).empty() && !std::string(y).empty() && weight2 != 0) {
            add_edge(1, 2, weight2, g);
        }

        
        std::vector<boost::graph_traits<Graph>::edge_descriptor> mst;
        kruskal_minimum_spanning_tree(g, std::back_inserter(mst));

        
        float total_weight = 0;
        boost::property_map<Graph, boost::edge_weight_t>::type weight_map 
            = get(boost::edge_weight, g);
        
        for (const auto& edge : mst) {
            total_weight += boost::get(weight_map, edge);
        }

        return total_weight;
    }
};