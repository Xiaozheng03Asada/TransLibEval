#include <string>
#include <algorithm>

class StringProcessor {
public:
    int calculate_levenshtein_distance(const std::string& str1, const std::string& str2) {
        size_t len1 = str1.size();
        size_t len2 = str2.size();
        std::vector<std::vector<size_t>> dp(len1 + 1, std::vector<size_t>(len2 + 1));

        for (size_t i = 0; i <= len1; ++i) {
            for (size_t j = 0; j <= len2; ++j) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = std::min({dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + (str1[i - 1] == str2[j - 1] ? 0 : 1)});
                }
            }
        }

        return dp[len1][len2];
    }
};