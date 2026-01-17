#include <string>
#include <boost/algorithm/string.hpp>
#include <map>
#include <vector>
#include <numeric>
class NumberDictManager {
public:
    std::string manage_number_dict(const std::string& operations_str) {
        std::map<int, int> number_dict;
        std::vector<std::string> operations;
        boost::split(operations, operations_str, boost::is_any_of(";"));
    
        for (const auto& op : operations) {
            if (op.empty()) continue;
    
            std::vector<std::string> op_parts;
            boost::split(op_parts, op, boost::is_any_of(","));
            
            if (op_parts[0] == "add") {
                int key = std::stoi(op_parts[1]);
                int value = std::stoi(op_parts[2]);
                number_dict[key] = value;
            }
            else if (op_parts[0] == "remove") {
                int key = std::stoi(op_parts[1]);
                number_dict.erase(key);
            }
            else if (op_parts[0] == "get") {
                int key = std::stoi(op_parts[1]);
                auto it = number_dict.find(key);
                return (it != number_dict.end()) ? std::to_string(it->second) : "default";
            }
            else if (op_parts[0] == "sort") {
                std::string result;
                for (const auto& pair : number_dict) {
                    if (!result.empty()) result += " ";
                    result += std::to_string(pair.first) + ":" + std::to_string(pair.second);
                }
                return result;
            }
            else if (op_parts[0] == "sum") {
                int sum = std::accumulate(number_dict.begin(), number_dict.end(), 0,
                    [](int acc, const std::pair<int, int>& pair) { return acc + pair.second; });
                return std::to_string(sum);
            }
        }
    
        return "";
    }
};