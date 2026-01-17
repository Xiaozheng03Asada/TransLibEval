#include <unordered_set>
#include <string>
#include <stdexcept>
#include <algorithm>

class JaccardExample {
public:
    static double compute_jaccard_distance(const std::string& string1, const std::string& string2) {
        if (string1.empty() || string2.empty()) {
            throw std::invalid_argument("Input(s) must not be empty");
        }

        auto generateBigrams = [](const std::string& s) -> std::unordered_set<std::string> {
            std::unordered_set<std::string> ngrams;
            for (size_t i = 0; i < s.length() - 1; ++i) {
                ngrams.insert(s.substr(i, 2));
            }
            return ngrams;
        };

        std::unordered_set<std::string> set1 = generateBigrams(string1);
        std::unordered_set<std::string> set2 = generateBigrams(string2);

        if (set1.empty() && set2.empty()) {
            return 0.0;
        }

        std::unordered_set<std::string> intersection;
        std::unordered_set<std::string> unionSet;

        for (const auto& item : set1) {
            if (set2.find(item) != set2.end()) {
                intersection.insert(item);
            }
            unionSet.insert(item);
        }

        for (const auto& item : set2) {
            unionSet.insert(item);
        }

        double similarity = static_cast<double>(intersection.size()) / unionSet.size();
        return 1 - similarity;
    }
};