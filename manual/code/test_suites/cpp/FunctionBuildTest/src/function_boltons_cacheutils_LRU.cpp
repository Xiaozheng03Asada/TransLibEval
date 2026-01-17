#include <iostream>
#include <unordered_map>
#include <list>
#include <vector>
#include <sstream>
#include <stdexcept>

class LRUCacheManager {
private:
    int capacity;
    std::unordered_map<std::string, std::pair<std::string, std::list<std::string>::iterator>> cache;
    std::list<std::string> lru;

    void touch(std::string key) {
        lru.erase(cache[key].second);
        lru.push_front(key);
        cache[key].second = lru.begin();
    }

public:
    LRUCacheManager(int size) : capacity(size) {}

    void set(std::string key, std::string value) {
        if (cache.find(key) != cache.end()) {
            touch(key);
            cache[key].first = value;
        } else {
            if (cache.size() == capacity) {
                cache.erase(lru.back());
                lru.pop_back();
            }
            lru.push_front(key);
            cache[key] = {value, lru.begin()};
        }
    }

    std::string get(std::string key) {
        if (cache.find(key) == cache.end()) {
            return "None";
        }
        touch(key);
        return cache[key].first;
    }
};

std::string manage_cache_operations(int cache_size, std::string operations_str) {
    if (operations_str.empty()) {
        return "";
    }

    LRUCacheManager cacheManager(cache_size);
    std::vector<std::string> results;
    std::istringstream iss(operations_str);
    std::string token;
    std::vector<std::string> operations;

    while (std::getline(iss, token, ',')) {
        operations.push_back(token);
    }

    int i = 0;
    while (i < operations.size()) {
        std::string operation_type = operations[i];
        std::string key = operations[i + 1];

        if (operation_type == "set") {
            std::string value = operations[i + 2];
            cacheManager.set(key, value);
            i += 3;
        } else if (operation_type == "get") {
            std::string result = cacheManager.get(key);
            results.push_back(result == "None" ? "None" : result);
            i += 2;
        } else {
            throw std::invalid_argument("Invalid operation type");
        }
    }

    std::ostringstream oss;
    for (size_t j = 0; j < results.size(); ++j) {
        oss << results[j];
        if (j < results.size() - 1) {
            oss << ",";
        }
    }

    return oss.str();
}