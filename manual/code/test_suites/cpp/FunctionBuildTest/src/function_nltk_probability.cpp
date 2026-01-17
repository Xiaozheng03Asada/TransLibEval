#include <map>
#include <algorithm>
#include <string>
#include <stdexcept>
#include <sstream>

class ProbabilityExample {
public:
    std::string compute_frequency(const std::string& data) {
        if (data.empty()) {
            throw std::invalid_argument("Input cannot be null");
        }

        std::map<char, int> freqMap;
        for (char ch : data) {
            freqMap[ch]++;
        }

        if (freqMap.empty()) {
            return "";
        }

        std::ostringstream result;
        for (const auto& pair : freqMap) {
            result << pair.first << ":" << pair.second << ", ";
        }

        std::string output = result.str();
        if (!output.empty()) {
            output.pop_back(); // Remove the last comma
            output.pop_back(); // Remove the last space
        }

        return output;
    }
};