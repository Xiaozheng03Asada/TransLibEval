#include <string>
#include <boost/multiprecision/cpp_dec_float.hpp>

class InfiniteCheck {
public:
    std::string check_discount_for_large_order(const std::string& value) {
        try {
            
            if (value == "Infinity" || value == "+Infinity" || value == "-Infinity") {
                return "True";
            }
            
            
            boost::multiprecision::cpp_dec_float_50 decimal_value(value);
            
            
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