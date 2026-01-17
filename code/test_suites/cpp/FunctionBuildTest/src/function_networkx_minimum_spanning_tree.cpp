#include <jgrapht/jgrapht_all.h>
#include <string>
#include <optional>

using namespace jgrapht;
using namespace jgrapht::alg;
using namespace jgrapht::graph;

class NetworkXExample {
public:
    float compute_minimum_spanning_tree(const std::string& u, const std::string& v, float weight, 
                                        const std::optional<std::string>& x, const std::optional<std::string>& y, 
                                        const std::optional<float>& weight2) {
        auto graph = SimpleWeightedGraph<std::string, DefaultWeightedEdge>();

        graph.addVertex(u);
        graph.addVertex(v);
        auto e1 = graph.addEdge(u, v);
        graph.setEdgeWeight(e1, weight);

        if (x && !x->empty() && y && !y->empty() && weight2 && *weight2 != 0.0f) {
            graph.addVertex(*x);
            graph.addVertex(*y);
            auto e2 = graph.addEdge(*x, *y);
            graph.setEdgeWeight(e2, *weight2);
        }

        auto mstAlgo = KruskalMinimumSpanningTree<std::string, DefaultWeightedEdge>(graph);
        double totalWeight = mstAlgo.getSpanningTree().getWeight();
        return static_cast<float>(totalWeight);
    }
};