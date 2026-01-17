#include <string>
#include <unordered_map>
#include <algorithm>

class StringProcessor {
public:
    static double calculate_setratio(const std::string& str1, const std::string& str2) {
        std::string s1 = str1.empty() ? "" : str1;
        std::string s2 = str2.empty() ? "" : str2;

        if (s1.empty() && s2.empty()) {
            return 1.0;
        }

        std::unordered_map<char, int> bag1;
        std::unordered_map<char, int> bag2;

        for (char ch : s1) {
            bag1[ch]++;
        }

        for (char ch : s2) {
            bag2[ch]++;
        }

        int common = 0;
        for (const auto& pair : bag1) {
            char ch = pair.first;
            common += std::min(bag1[ch], bag2[ch]);
        }

        return (2.0 * common) / (s1.length() + s2.length());
    }
};