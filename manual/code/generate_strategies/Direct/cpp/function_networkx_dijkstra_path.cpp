#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/dijkstra_shortest_paths.hpp>
#include <string>
#include <vector>
#include <map>

class NetworkXExample {
public:
    template<typename T1, typename T2, typename T3, typename T4, typename T5>
    std::string compute_shortest_path(
        const T1& start, const T2& end,
        const T3& u, const T4& v, float weight,
        const T5& x = T5(), const T5& y = T5(), float weight2 = 0) {
        
        using EdgeProperty = boost::property<boost::edge_weight_t, float>;
        using Graph = boost::adjacency_list<boost::vecS, boost::vecS, 
                                          boost::undirectedS,
                                          boost::no_property,
                                          EdgeProperty>;
        Graph g;

        // 创建节点映射
        std::map<std::string, int> node_map;
        auto get_vertex = [&](const std::string& name) {
            if (node_map.find(name) == node_map.end()) {
                int idx = node_map.size();
                node_map[name] = idx;
                return boost::add_vertex(g);
            }
            return boost::vertex(node_map[name], g);
        };

        // 添加第一条边
        std::string u_str = std::string(u);
        std::string v_str = std::string(v);
        add_edge(get_vertex(u_str), get_vertex(v_str), EdgeProperty(weight), g);

        // 添加第二条边（如果存在）
        if (!std::string(x).empty() && !std::string(y).empty() && weight2 != 0) {
            std::string x_str = std::string(x);
            std::string y_str = std::string(y);
            add_edge(get_vertex(x_str), get_vertex(y_str), EdgeProperty(weight2), g);
        }

        // 计算最短路径
        std::vector<int> predecessor(num_vertices(g));
        std::vector<float> distance(num_vertices(g));
        
        try {
            std::string start_str = std::string(start);
            std::string end_str = std::string(end);
            
            if (node_map.find(start_str) == node_map.end() ||
                node_map.find(end_str) == node_map.end()) {
                return "no path";
            }

            boost::dijkstra_shortest_paths(g, get_vertex(start_str),
                boost::predecessor_map(&predecessor[0])
                .distance_map(&distance[0]));

            // 重建路径
            std::vector<std::string> path;
            int current = node_map[end_str];
            if (distance[current] == std::numeric_limits<float>::max()) {
                return "no path";
            }

            while (current != node_map[start_str]) {
                for (const auto& pair : node_map) {
                    if (pair.second == current) {
                        path.insert(path.begin(), pair.first);
                        break;
                    }
                }
                current = predecessor[current];
            }
            path.insert(path.begin(), start_str);

            // 构建结果字符串
            std::string result = path[0];
            for (size_t i = 1; i < path.size(); ++i) {
                result += " -> " + path[i];
            }
            return result;
        }
        catch (...) {
            return "no path";
        }
    }
};