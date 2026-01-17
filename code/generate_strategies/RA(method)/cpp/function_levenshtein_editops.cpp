#include <string>
#include <vector>
#include <sstream>
#include <tuple>
#include <boost/any.hpp>

class StringProcessor {
public:
    std::string calculate_editops(const boost::any& a1, const boost::any& a2) {
        if (a1.type() != typeid(std::string) || a2.type() != typeid(std::string)) {
            return "Error: Both inputs must be strings";
        }

        const std::string& str1 = boost::any_cast<const std::string&>(a1);
        const std::string& str2 = boost::any_cast<const std::string&>(a2);
        
        std::vector<std::tuple<std::string, int, int>> operations;
        int m = str1.length();
        int n = str2.length();
        std::vector<std::vector<int>> dp(m + 1, std::vector<int>(n + 1));
        
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1[i-1] == str2[j-1])
                    dp[i][j] = dp[i-1][j-1];
                else
                    dp[i][j] = 1 + std::min({dp[i-1][j], dp[i][j-1], dp[i-1][j-1]});
            }
        }
        
        // 修改回溯逻辑，优先考虑后面的位置
        int i = m, j = n;
        while (i > 0 || j > 0) {
            if (j > 0 && dp[i][j] == dp[i][j-1] + 1) {
                operations.insert(operations.begin(), std::make_tuple("insert", i, j-1));
                j--;
            }
            else if (i > 0 && dp[i][j] == dp[i-1][j] + 1) {
                operations.insert(operations.begin(), std::make_tuple("delete", i-1, j));
                i--;
            }
            else if (i > 0 && j > 0) {
                if (str1[i-1] == str2[j-1]) {
                    i--; j--;
                } else {
                    operations.insert(operations.begin(), std::make_tuple("replace", i-1, j-1));
                    i--; j--;
                }
            }
        }
        
        std::ostringstream oss;
        oss << "[";
        for (size_t i = 0; i < operations.size(); i++) {
            if (i > 0) oss << ", ";
            oss << "('" << std::get<0>(operations[i]) << "', "
                << std::get<1>(operations[i]) << ", "
                << std::get<2>(operations[i]) << ")";
        }
        oss << "]";
        return operations.empty() ? "[]" : oss.str();
    }
};