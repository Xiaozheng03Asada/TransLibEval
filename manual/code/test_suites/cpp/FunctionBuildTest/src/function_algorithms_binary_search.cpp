#include <string>
#include <vector>
#include <algorithm>

class BinarySearchFunction {
public:
    int binary_search_index(const std::string& sortedString, const std::string& target) {
        if (target.empty() || target.length() != 1) return -1;
        char targetChar = target[0];
        auto it = std::find(sortedString.begin(), sortedString.end(), targetChar);
        if (it != sortedString.end()) {
            return std::distance(sortedString.begin(), it);
        }
        return -1;
    }
};