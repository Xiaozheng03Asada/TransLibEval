#include <string>
#include <boost/any.hpp>
#include <fmt/format.h>
#include <fmt/ranges.h>
#include <vector>
#include <utility>

class ProbabilityExample {
    public:
        std::string compute_frequency(const boost::any& data) {
            try {
                // 类型检查和转换
                std::string input;
                if (data.type() == typeid(std::string)) {
                    input = boost::any_cast<std::string>(data);
                }
                else if (data.type() == typeid(const char*)) {
                    input = boost::any_cast<const char*>(data);
                }
                else {
                    throw std::invalid_argument("Input must be string");
                }
    
                if (input.empty()) {
                    return "";
                }
    
                // 使用vector保持顺序
                std::vector<std::pair<char, int>> freq_dist;
                for (char c : input) {
                    // 查找字符是否已存在
                    bool found = false;
                    for (auto& pair : freq_dist) {
                        if (pair.first == c) {
                            pair.second++;
                            found = true;
                            break;
                        }
                    }
                    // 如果不存在，添加新条目
                    if (!found) {
                        freq_dist.emplace_back(c, 1);
                    }
                }
    
                // 格式化输出
                std::string result;
                bool first = true;
                for (const auto& [key, value] : freq_dist) {
                    if (!first) {
                        result += ", ";
                    }
                    result += fmt::format("{}:{}", key, value);
                    first = false;
                }
    
                return result;
            }
            catch (const boost::bad_any_cast&) {
                throw std::invalid_argument("Input must be string");
            }
        }
    };
