#include <string>
#include <sstream>
#include <iomanip>
#include <boost/multiprecision/cpp_dec_float.hpp>

class SetDecimalPrecision {
public:
    std::string check_discount_for_large_order(const std::string& value, int precision) {
        try {
            // 将字符串转换为高精度小数
            boost::multiprecision::cpp_dec_float_50 decimal_value(value);
            
            // 设置输出格式
            std::ostringstream ss;
            ss << std::fixed << std::setprecision(precision) << decimal_value;
            
            // 获取结果字符串并移除尾随的零（除非是整数）
            std::string result = ss.str();
            size_t dot_pos = result.find('.');
            if (dot_pos != std::string::npos) {
                // 移除尾随的零，但保留小数点后指定的位数
                size_t last_non_zero = result.find_last_not_of('0');
                if (last_non_zero > dot_pos && last_non_zero < result.length() - 1) {
                    result.erase(last_non_zero + 1);
                }
                // 确保至少有 precision 位小数
                size_t decimal_places = result.length() - dot_pos - 1;
                while (decimal_places < precision) {
                    result += '0';
                    decimal_places++;
                }
            }
            
            return result;
        }
        catch (const std::exception&) {
            return "Error";
        }
    }
};