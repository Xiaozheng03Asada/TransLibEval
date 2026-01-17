#include <string>
#include <stdexcept>

class StringProcessor {
public:
    std::string calculate_hamming_distance(const std::string& str1, const std::string& str2) {
        if (str1.empty() || str2.empty()) {
            return "Error: Both inputs must be strings";
        }
        if (str1.length() != str2.length()) {
            return "Strings must be of the same length for Hamming distance.";
        }
        try {
            int distance = hamming_distance(str1, str2);
            return std::to_string(distance);
        } catch (const std::invalid_argument& e) {
            return "Error: Both inputs must be strings";
        }
    }

private:
    int hamming_distance(const std::string& str1, const std::string& str2) {
        int distance = 0;
        for (size_t i = 0; i < str1.length(); ++i) {
            if (str1[i] != str2[i]) {
                ++distance;
            }
        }
        return distance;
    }
};