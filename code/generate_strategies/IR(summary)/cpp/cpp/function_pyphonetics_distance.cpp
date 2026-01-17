#include <string>
#include <stdexcept>
#include <boost/algorithm/string/trim.hpp>
#include <boost/algorithm/string/case_conv.hpp>
#include <boost/algorithm/string/predicate.hpp>
#include <vector>
#include <algorithm>

class SoundexProcessor {
public:
    std::string compute_distance(const std::string& word1, const std::string& word2, const std::string& metric) {
        try {
            std::string s1 = word1;
            std::string s2 = word2;
            std::string m = metric;

            boost::trim(s1);
            boost::trim(s2);
            boost::to_lower(s1);
            boost::to_lower(s2);

            if (m == "refined") {
                
                std::vector<std::vector<int>> dp(s1.size() + 1, std::vector<int>(s2.size() + 1));
        
                for (size_t i = 0; i <= s1.size(); ++i) {
                    dp[i][0] = i;
                }
                for (size_t j = 0; j <= s2.size(); ++j) {
                    dp[0][j] = j;
                }
        
                for (size_t i = 1; i <= s1.size(); ++i) {
                    for (size_t j = 1; j <= s2.size(); ++j) {
                        if (s1[i - 1] == s2[j - 1]) {
                            dp[i][j] = dp[i - 1][j - 1];
                        } else {
                            dp[i][j] = 1 + std::min({
                                dp[i - 1][j],  
                                dp[i][j - 1],   
                                dp[i - 1][j - 1] 
                            });
                        }
                    }
                }
                return std::to_string(dp[s1.size()][s2.size()]);
            }
            else if (m == "hamming") {
                if (s1.size() != s2.size()) {
                    return "1";
                }
                int distance = 0;
                for (size_t i = 0; i < s1.size(); ++i) {
                    if (s1[i] != s2[i]) {
                        distance++;
                    }
                }
                
                return (distance > 1) ? "1" : std::to_string(distance);
            }
            else {
                return "Invalid metric. Only 'refined' and 'hamming' are supported.";
            }
        }
        catch (...) {
            throw std::invalid_argument("TypeError: Inputs must be strings");
        }
    }
};