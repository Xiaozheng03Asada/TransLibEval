#include <string>
#include <vector>
#include <map>
#include <algorithm>
#include <sstream>
#include <cctype>

class WordCounter {
public:
    std::string count_words(const std::string& word_string) {
        if (word_string.empty()) {
            return "";
        }

        std::vector<std::string> words;
        std::stringstream ss(word_string);
        std::string word;
        while (std::getline(ss, word, ',')) {
            words.push_back(trim(word));
        }

        std::map<std::string, int> wordCount;
        for (const auto& w : words) {
            std::string lowerWord = to_lower(w);
            wordCount[lowerWord]++;
        }

        std::stringstream result;
        for (const auto& entry : wordCount) {
            result << entry.first << ":" << entry.second << ";";
        }

        std::string result_str = result.str();
        if (!result_str.empty()) {
            result_str.pop_back();
        }
        return result_str;
    }

private:
    std::string trim(const std::string& str) {
        size_t first = str.find_first_not_of(' ');
        if (first == std::string::npos) return "";
        size_t last = str.find_last_not_of(' ');
        return str.substr(first, last - first + 1);
    }

    std::string to_lower(const std::string& str) {
        std::string lowerStr = str;
        std::transform(lowerStr.begin(), lowerStr.end(), lowerStr.begin(), ::tolower);
        return lowerStr;
    }
};