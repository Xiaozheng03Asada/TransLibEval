#include <vector>
#include <string>
#include <sstream>
#include <algorithm>

class LinearSearchFunction {
public:
    int linear_search_from_string(const std::string& arr, const std::string& target) {
        if (arr.empty()) {
            return -1;
        }

        std::vector<int> numbers;
        std::stringstream ss(arr);
        std::string token;
        while (std::getline(ss, token, ',')) {
            numbers.push_back(std::stoi(token));
        }

        int targetNum = std::stoi(target);

        for (size_t i = 0; i < numbers.size(); ++i) {
            if (numbers[i] == targetNum) {
                return i;
            }
        }
        return -1;
    }
};