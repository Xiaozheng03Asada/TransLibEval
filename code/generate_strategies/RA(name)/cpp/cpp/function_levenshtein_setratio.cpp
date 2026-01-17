#include <string>
#include <boost/unordered_map.hpp>
#include <algorithm>

class StringProcessor {
public:
    double calculate_setratio(const std::string& str1, const std::string& str2) {
       
        boost::unordered_map<char, int> freq1, freq2;
        for (char c : str1)
            freq1[c]++;
        for (char c : str2)
            freq2[c]++;

        // 计算多集合交集的元素总数（取各字符最小出现次数之和）
        int intersection = 0;
        for (const auto& pair : freq1) {
            char c = pair.first;
            if (freq2.find(c) != freq2.end()) {
                intersection += std::min(pair.second, freq2[c]);
            }
        }

        // 总字符数（包括重复字符）
        int total = str1.length() + str2.length();

        // 处理两个空字符串的情况
        if (total == 0)
            return 1.0;

        // 相似度公式：2 * 交集总数 / 总字符数
        return (2.0 * intersection) / total;
    }};