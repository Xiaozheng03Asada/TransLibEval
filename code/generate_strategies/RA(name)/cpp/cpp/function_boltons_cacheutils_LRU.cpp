#include <string>
#include <boost/unordered_map.hpp>
#include <boost/circular_buffer.hpp>
#include <vector>
#include <sstream>
#include <algorithm>
class LRUCacheManager {
public:
    std::string manage_cache_operations(int cache_size, const std::string& operations_str) {
        boost::unordered_map<std::string, std::string> cache_map;
        boost::circular_buffer<std::string> cache_queue(cache_size);
        int cache_capacity = cache_size;
    
        std::vector<std::string> results;
    
        if (operations_str.empty()) {
            return "";
        }
    
        auto split = [&](const std::string& s, char delimiter) {
            std::vector<std::string> tokens;
            std::string token;
            std::istringstream tokenStream(s);
            while (std::getline(tokenStream, token, delimiter)) {
                tokens.push_back(token);
            }
            return tokens;
        };
    
        auto update_lru = [&](const std::string& key) {
            auto it = std::find(cache_queue.begin(), cache_queue.end(), key);
            if (it != cache_queue.end()) {
                cache_queue.erase(it);
            }
            cache_queue.push_back(key);
        };
    
        auto operations = split(operations_str, ',');
        for (size_t i = 0; i < operations.size();) {
            std::string operation_type = operations[i];
            std::string key = operations[i + 1];
    
            if (operation_type == "set") {
                std::string value = operations[i + 2];
                if (cache_map.find(key) == cache_map.end() && cache_map.size() == cache_capacity) {
                    std::string lru_key = cache_queue.front();
                    cache_map.erase(lru_key);
                    cache_queue.pop_front();
                }
                cache_map[key] = value;
                update_lru(key);
                i += 3;
            } else if (operation_type == "get") {
                auto it = cache_map.find(key);
                if (it != cache_map.end()) {
                    results.push_back(it->second);
                    update_lru(key);
                } else {
                    results.push_back("None");
                }
                i += 2;
            } else {
                throw std::invalid_argument("Invalid operation type");
            }
        }
    
        std::string result;
        for (size_t i = 0; i < results.size(); i++) {
            if (i > 0) result += ",";
            result += results[i];
        }
        return result;
    }
};