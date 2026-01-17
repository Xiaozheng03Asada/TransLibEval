#include <string>
#include <algorithm>
#include <cmath>
#include "RefinedSoundex.h"

class SoundexProcessor {
public:
    static std::string compute_distance(const std::string& word1, const std::string& word2, const std::string& metric) {
        class DistanceCalculator {
        private:
            RefinedSoundex rs;

            int calculateRefinedDistance(const std::string& str1, const std::string& str2) {
                std::string code1 = rs.encode(str1);
                std::string code2 = rs.encode(str2);
                return calculateDifference(code1, code2);
            }

            int calculateHammingDistance(const std::string& str1, const std::string& str2) {
                int minLength = std::min(str1.length(), str2.length());
                int distance = std::abs(static_cast<int>(str1.length()) - static_cast<int>(str2.length()));

                for (int i = 0; i < minLength; i++) {
                    if (str1[i] != str2[i]) {
                        distance++;
                    }
                }

                return distance > 0 ? 1 : 0;
            }

            int calculateDifference(const std::string& str1, const std::string& str2) {
                int diff = 0;
                int length = std::min(str1.length(), str2.length());
                for (int i = 0; i < length; i++) {
                    if (str1[i] != str2[i]) {
                        diff += 1;
                    }
                }
                return diff;
            }

        public:
            std::string compute(const std::string& word1, const std::string& word2, const std::string& metric) {
                if (metric == "refined") {
                    return std::to_string(calculateRefinedDistance(word1, word2));
                } else if (metric == "hamming") {
                    return std::to_string(calculateHammingDistance(word1, word2));
                } else {
                    return "Invalid metric. Only 'refined' and 'hamming' are supported.";
                }
            }
        };

        DistanceCalculator calculator;
        return calculator.compute(word1, word2, metric);
    }
};