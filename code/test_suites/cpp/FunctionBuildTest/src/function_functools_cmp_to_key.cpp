#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <sstream>

class NumberDictManager {
public:
    std::string manage_number_dict(const std::string& operations_str) {
        std::vector<std::string> operations;
        std::stringstream ss(operations_str);
        std::string operation;
        while (std::getline(ss, operation, ';')) {
            operations.push_back(operation);
        }
        std::map<int, int> number_dict;

        auto comparator = [](const std::pair<int, int>& entry1, const std::pair<int, int>& entry2) {
            return entry1.first < entry2.first;
        };

        for (const auto& op : operations) {
            if (op.empty()) {
                continue;
            }
            std::stringstream op_ss(op);
            std::string op_type;
            std::getline(op_ss, op_type, ',');
            if (op_type == "add") {
                int keyAdd, valueAdd;
                op_ss >> keyAdd;
                op_ss.ignore();
                op_ss >> valueAdd;
                number_dict[keyAdd] = valueAdd;
            } else if (op_type == "remove") {
                int keyRemove;
                op_ss >> keyRemove;
                number_dict.erase(keyRemove);
            } else if (op_type == "get") {
                int keyGet;
                op_ss >> keyGet;
                auto it = number_dict.find(keyGet);
                if (it == number_dict.end()) {
                    return "default";
                } else {
                    return std::to_string(it->second);
                }
            } else if (op_type == "sort") {
                std::vector<std::pair<int, int>> sortedItems(number_dict.begin(), number_dict.end());
                std::sort(sortedItems.begin(), sortedItems.end(), comparator);
                std::stringstream sb;
                for (const auto& entry : sortedItems) {
                    if (sb.tellp() != 0) {
                        sb << " ";
                    }
                    sb << entry.first << ":" << entry.second;
                }
                return sb.str();
            } else if (op_type == "sum") {
                int sum = 0;
                for (const auto& entry : number_dict) {
                    sum += entry.second;
                }
                return std::to_string(sum);
            }
        }
        return "";
    }
};