#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <algorithm>

class LRUCacheManager {
public:
    std::string manage_lru_cache(const std::string& cache_size, const std::string& operations) {
        int capacity = std::stoi(cache_size);
        std::map<std::string, std::string> cache;
        std::vector<std::string> keys;

        std::stringstream resultBuilder;
        if (operations.empty()) {
            return "";
        }

        std::vector<std::string> ops = split(operations, ';');
        bool firstResult = true;

        for (const std::string& opStr : ops) {
            if (opStr.empty()) {
                continue;
            }
            std::vector<std::string> parts = split(opStr, ',');
            if (parts.size() < 2) {
                continue;
            }
            std::string command = parts[0];
            if (command == "set") {
                if (parts.size() < 3) {
                    continue;
                }
                std::string key = parts[1];
                std::string value = parts[2];
                if (cache.find(key) != cache.end()) {
                    keys.erase(std::remove(keys.begin(), keys.end(), key), keys.end());
                }
                cache[key] = value;
                keys.push_back(key);
                if (cache.size() > capacity) {
                    std::string lruKey = keys.front();
                    keys.erase(keys.begin());
                    cache.erase(lruKey);
                }
            } else if (command == "get") {
                std::string key = parts[1];
                std::string value = (cache.find(key) != cache.end()) ? cache[key] : "None";
                if (!firstResult) {
                    resultBuilder << ",";
                } else {
                    firstResult = false;
                }
                resultBuilder << value;
            }
        }

        return resultBuilder.str();
    }

private:
    std::vector<std::string> split(const std::string& s, char delimiter) {
        std::vector<std::string> tokens;
        std::string token;
        std::istringstream tokenStream(s);
        while (std::getline(tokenStream, token, delimiter)) {
            tokens.push_back(token);
        }
        return tokens;
    }
};