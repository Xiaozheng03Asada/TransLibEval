#include <string>
#include <map>
#include <boost/any.hpp>
#include <boost/algorithm/string.hpp>
#include <boost/lexical_cast.hpp>
#include <vector>

class WordCounter {
public:
    std::string count_words(const boost::any& input){
        try {
            const std::string& word_string = boost::any_cast<std::string>(input);
            
            if (word_string.empty()) {
                return "";
            }
    
            std::vector<std::string> words;
            boost::split(words, word_string, boost::is_any_of(","));
    
            // 使用保持插入顺序的数据结构
            std::map<std::string, int> word_count;
            std::vector<std::string> word_order; // 保存第一次出现的顺序
            
            for (auto& word : words) {
                boost::trim(word);
                boost::to_lower(word);
                if (word_count.find(word) == word_count.end()) {
                    word_order.push_back(word); // 记录首次出现的顺序
                }
                word_count[word]++;
            }
    
            std::string result;
            // 按首次出现顺序构建结果
            for (const auto& word : word_order) {
                if (!result.empty()) {
                    result += ";";
                }
                result += word + ":" + std::to_string(word_count[word]);
            }
    
            return result;
        }
        catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Input should be a string");
        }
    }
};