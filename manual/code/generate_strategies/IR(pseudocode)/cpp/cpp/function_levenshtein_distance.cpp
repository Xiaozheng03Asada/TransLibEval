#include <string>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <boost/any.hpp>
#include <typeinfo>
#include <stdexcept>
class StringProcessor {
public:
    int calculate_levenshtein_distance(const boost::any &a, const boost::any &b) {
        if (a.type() != typeid(std::string) || b.type() != typeid(std::string)) {
            throw std::invalid_argument("Input is not a string");
        }
        const std::string &str1 = boost::any_cast<const std::string&>(a);
        const std::string &str2 = boost::any_cast<const std::string&>(b);
        
        size_t m = str1.size(), n = str2.size();
        std::vector<std::vector<int>> dp(m + 1, std::vector<int>(n + 1, 0));
        for (size_t i = 0; i <= m; i++) {
            dp[i][0] = static_cast<int>(i);
        }
        for (size_t j = 0; j <= n; j++) {
            dp[0][j] = static_cast<int>(j);
        }
        for (size_t i = 1; i <= m; i++) {
            for (size_t j = 1; j <= n; j++) {
                int cost = (str1[i - 1] == str2[j - 1]) ? 0 : 1;
                dp[i][j] = std::min({ dp[i - 1][j] + 1,
                                      dp[i][j - 1] + 1,
                                      dp[i - 1][j - 1] + cost });
            }
        }
        return dp[m][n];
    }
};