#include <string>
#include <boost/algorithm/string.hpp>
#include <type_traits>

class SoundexProcessor {
public:
    template<typename T1, typename T2>
    std::string compare_strings(const T1& str1, const T2& str2) {
        try {
            std::string s1 = std::string(str1);
            std::string s2 = std::string(str2);
            
            if (s1.empty() || s2.empty()) {
                return "The given string is empty.";
            }

            // 生成第一个字符串的 Soundex 编码
            std::string code1;
            {
                std::string upper_s1 = boost::to_upper_copy(s1);
                code1 += upper_s1[0];
                
                for (size_t i = 1; i < upper_s1.length(); ++i) {
                    char c = upper_s1[i];
                    char code = '0';
                    
                    switch (c) {
                        case 'B': case 'F': case 'P': case 'V':
                            code = '1'; break;
                        case 'C': case 'G': case 'J': case 'K': 
                        case 'Q': case 'S': case 'X': case 'Z':
                            code = '2'; break;
                        case 'D': case 'T':
                            code = '3'; break;
                        case 'L':
                            code = '4'; break;
                        case 'M': case 'N':
                            code = '5'; break;
                        case 'R':
                            code = '6'; break;
                        default:
                            code = '0';
                    }
                    if (code != '0' && code != code1.back()) {
                        code1 += code;
                    }
                }
                code1.resize(4, '0');
            }

            // 生成第二个字符串的 Soundex 编码
            std::string code2;
            {
                std::string upper_s2 = boost::to_upper_copy(s2);
                code2 += upper_s2[0];
                
                for (size_t i = 1; i < upper_s2.length(); ++i) {
                    char c = upper_s2[i];
                    char code = '0';
                    
                    switch (c) {
                        case 'B': case 'F': case 'P': case 'V':
                            code = '1'; break;
                        case 'C': case 'G': case 'J': case 'K': 
                        case 'Q': case 'S': case 'X': case 'Z':
                            code = '2'; break;
                        case 'D': case 'T':
                            code = '3'; break;
                        case 'L':
                            code = '4'; break;
                        case 'M': case 'N':
                            code = '5'; break;
                        case 'R':
                            code = '6'; break;
                        default:
                            code = '0';
                    }
                    if (code != '0' && code != code2.back()) {
                        code2 += code;
                    }
                }
                code2.resize(4, '0');
            }
            
            return (code1 == code2) ? "true" : "false";
        }
        catch (...) {
            return "The given string is empty.";
        }
    }
};