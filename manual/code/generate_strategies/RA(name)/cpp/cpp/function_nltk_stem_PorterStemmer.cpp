#include <string>
#include <boost/any.hpp>
#include <boost/algorithm/string.hpp>

class Stemmer {
public:
    std::string test_stem(const boost::any& word){ try {
        // 类型检查和转换
        std::string input;
        if (word.type() == typeid(std::string)) {
            input = boost::any_cast<std::string>(word);
        }
        else if (word.type() == typeid(const char*)) {
            input = boost::any_cast<const char*>(word);
        }
        else {
            throw std::invalid_argument("Input must be string");
        }

        if (input.empty()) {
            return "";
        }

        // 转换为小写进行比较
        std::string lower_word = input;
        boost::to_lower(lower_word);

        // 处理后缀'ing'
        if (boost::algorithm::iends_with(lower_word, "ing") && lower_word.length() > 4) {
            std::string stemmed = input.substr(0, input.length() - 3);
            // 处理重复辅音
            if (stemmed.length() >= 2 && 
                stemmed[stemmed.length()-1] == stemmed[stemmed.length()-2]) {
                stemmed = stemmed.substr(0, stemmed.length()-1);
            }
            return stemmed;
        }

        // 处理后缀'ed'
        if (boost::algorithm::iends_with(lower_word, "ed")) {
            std::string stemmed = input.substr(0, input.length() - 2);
            // 处理重复辅音
            if (stemmed.length() >= 2 && 
                stemmed[stemmed.length()-1] == stemmed[stemmed.length()-2]) {
                stemmed = stemmed.substr(0, stemmed.length()-1);
            }
            return stemmed;
        }

        // 处理后缀's'
        if (boost::algorithm::iends_with(lower_word, "s") && lower_word.length() > 1) {
            return input.substr(0, input.length() - 1);
        }

        return input;
    }
    catch (const boost::bad_any_cast&) {
        throw std::invalid_argument("Input must be string");
    }}
};