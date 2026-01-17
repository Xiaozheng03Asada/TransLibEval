#include <string>
#include <fmt/format.h>
#include <type_traits>
#include <vector>
#include <boost/algorithm/string.hpp>

class ValidateTuple {
public:
    template<typename T>
    std::string validate(const T& input_str) {
        try {
            // 检查输入类型是否可以转换为字符串
            if constexpr (!std::is_convertible_v<T, std::string>) {
                return "Invalid input: Must be a pair of values";
            }

            std::string str = std::string(input_str);
            std::vector<std::string> parts;
            boost::split(parts, str, boost::is_any_of(","));

            // 检查是否为一对值
            if (parts.size() != 2) {
                return "Invalid input: Must be a pair of values";
            }

            // 去除空白
            boost::trim(parts[0]);
            boost::trim(parts[1]);

            // 检查第一个值是否为整数
            for (char c : parts[0]) {
                if (!std::isdigit(c)) {
                    return "Invalid input: First value must be an integer";
                }
            }

            // 返回格式化结果
            return fmt::format("Valid tuple: ({}, {})", parts[0], parts[1]);
        }
        catch (...) {
            return "Invalid input: Must be a pair of values";
        }
    }
};