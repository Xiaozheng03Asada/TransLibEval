#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <regex>

class CounterCalculator {
public:
    std::string count_elements(const std::string& data) {
        if (data.empty()) return "failed";

        // 使用正则表达式分割字符串并过滤空元素
        std::regex regex("\\s+");
        std::vector<std::string> words(std::sregex_token_iterator(data.begin(), data.end(), regex, -1), std::sregex_token_iterator());

        if (words.empty()) return "failed";

        // 使用map保持元素插入顺序
        std::map<std::string, int> counter;
        for (const auto& word : words) {
            counter[word]++;
        }

        // 构建结果字符串
        std::vector<std::string> entries;
        for (const auto& entry : counter) {
            entries.push_back(entry.first + ":" + std::to_string(entry.second));
        }

        std::ostringstream result;
        for (size_t i = 0; i < entries.size(); ++i) {
            if (i != 0) result << ", ";
            result << entries[i];
        }

        return result.str();
    }
};