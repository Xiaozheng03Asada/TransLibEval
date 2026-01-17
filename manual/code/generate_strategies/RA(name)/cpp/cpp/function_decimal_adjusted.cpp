#include <string>
#include <sstream>
#include <iomanip>
#include <cmath>
#include <boost/multiprecision/cpp_dec_float.hpp>


class GetDecimalAdjusted {
public:
    std::string check_discount_for_large_order(double value) {
        try {
            boost::multiprecision::cpp_dec_float_50 decimal_value(value);

            if (decimal_value == 0) {
                return "-Infinity";
            }

            // 使用绝对值计算 adjusted
            boost::multiprecision::cpp_dec_float_50 abs_value = abs(decimal_value);
            int adjusted = static_cast<int>(floor(log10(abs_value)));

            return std::to_string(adjusted);
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};