#include <string>
#include <sstream>
#include <boost/multiprecision/cpp_dec_float.hpp>
#include <boost/lexical_cast.hpp>

class GetDecimalTuple {
public:
    std::string check_discount_for_large_order(const std::string& value) {
        try {
            // 使用 boost 的高精度浮点数
            boost::multiprecision::cpp_dec_float_50 decimal_value = 
                boost::lexical_cast<boost::multiprecision::cpp_dec_float_50>(value);

            // 获取符号 (0 表示正数，1 表示负数)
            int sign = decimal_value < 0 ? 1 : 0;
            decimal_value = abs(decimal_value);

            // 转换为字符串并移除小数点
            std::string str_value = decimal_value.str(0, std::ios_base::fixed);
            str_value.erase(std::remove(str_value.begin(), str_value.end(), '.'), str_value.end());

            // 移除前导和尾随的零
            while (!str_value.empty() && str_value[0] == '0') {
                str_value.erase(0, 1);
            }
            while (!str_value.empty() && str_value.back() == '0') {
                str_value.pop_back();
            }

            // 如果字符串为空，说明是零
            if (str_value.empty()) {
                return "0,0,0";
            }

            // 计算指数
            size_t decimal_pos = value.find('.');
            int exponent = 0;
            if (decimal_pos != std::string::npos) {
                exponent = -(value.length() - decimal_pos - 1);
            }

            // 返回结果
            std::stringstream result;
            result << sign << "," << str_value << "," << exponent;
            return result.str();
        }
        catch (const std::exception&) {
            return "Error";
        }
    }
};