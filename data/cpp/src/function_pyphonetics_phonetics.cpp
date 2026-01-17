#include <string>
#include <boost/algorithm/string.hpp>
#include <boost/algorithm/string/case_conv.hpp>
#include <boost/algorithm/string/trim.hpp>
#include <boost/algorithm/string/replace.hpp>

class PhoneticProcessor {
public:
    std::string generate_soundex(const std::string &input) {
        if (input.empty())
            return "The given string is empty.";

        // 使用 boost 将输入转换为大写
        std::string word = boost::to_upper_copy(input);

        std::string soundex;
        soundex.push_back(word[0]);

        auto mapChar = [](char c) -> char {
            switch (c) {
                case 'B': case 'F': case 'P': case 'V':
                    return '1';
                case 'C': case 'G': case 'J': case 'K':
                case 'Q': case 'S': case 'X': case 'Z':
                    return '2';
                case 'D': case 'T':
                    return '3';
                case 'L':
                    return '4';
                case 'M': case 'N':
                    return '5';
                case 'R':
                    return '6';
                default:
                    return '0';  // A, E, I, O, U, H, W, Y
            }
        };

        char last_digit = mapChar(word[0]);

        for (size_t i = 1; i < word.size(); i++) {
            char c = word[i];
            char digit = mapChar(c);
            if (digit == '0') {
                last_digit = '0';
                continue;
            }
            if (digit == last_digit)
                continue;
            soundex.push_back(digit);
            last_digit = digit;
        }

        if (soundex.size() > 4)
            soundex = soundex.substr(0, 4);
        while (soundex.size() < 4)
            soundex.push_back('0');

        return soundex;
    }
};