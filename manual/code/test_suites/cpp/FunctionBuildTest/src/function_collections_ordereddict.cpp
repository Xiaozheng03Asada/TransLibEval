#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <algorithm>

class OrderedDictCalculator {
public:
    std::string process_key_value_pairs(const std::string& data) {
        if (data.empty()) {
            return "failed";
        }

        std::map<std::string, std::string> orderedDict;
        std::vector<std::string> items;
        std::stringstream ss(data);
        std::string item;

        while (std::getline(ss, item, ',')) {
            item.erase(0, item.find_first_not_of(' '));
            item.erase(item.find_last_not_of(' ') + 1);
            if (item.empty()) {
                continue;
            }

            size_t colonPos = item.find(':');
            if (colonPos == std::string::npos) {
                continue;
            }

            std::string key = item.substr(0, colonPos);
            key.erase(0, key.find_first_not_of(' '));
            key.erase(key.find_last_not_of(' ') + 1);

            std::string value = item.substr(colonPos + 1);
            value.erase(0, value.find_first_not_of(' '));
            value.erase(value.find_last_not_of(' ') + 1);

            orderedDict[key] = value;
        }

        if (orderedDict.empty()) {
            return "failed";
        }

        std::stringstream result;
        bool first = true;
        for (const auto& entry : orderedDict) {
            if (!first) {
                result << ", ";
            }
            first = false;
            result << entry.first << ":" << entry.second;
        }

        return result.str();
    }
};