#include <string>
#include <boost/multiprecision/cpp_dec_float.hpp>

class InfiniteCheck {
public:
    std::string check_discount_for_large_order(const std::string& value) {
        try {
            // 特殊值的直接判断
            if (value == "Infinity" || value == "+Infinity" || value == "-Infinity") {
                return "True";
            }
            
            // 对于其他值，尝试转换为高精度数值类型
            boost::multiprecision::cpp_dec_float_50 decimal_value(value);
            
            // 检查是否为有限值
            if (isinf(decimal_value)) {
                return "True";
            }
            return "False";
        }
        catch (const std::exception&) {
            if (value == "NaN") {
                return "False";
            }
            return "Error";
        }
    }
};