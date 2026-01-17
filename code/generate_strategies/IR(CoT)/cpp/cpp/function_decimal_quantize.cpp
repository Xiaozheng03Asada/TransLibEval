#include <string>
#include <sstream>
#include <iomanip>
#include <boost/multiprecision/cpp_dec_float.hpp>

class SetDecimalPrecision {
public:
    std::string check_discount_for_large_order(const std::string& value, int precision) {
        try {
            
            boost::multiprecision::cpp_dec_float_50 decimal_value(value);
            
            
            std::ostringstream ss;
            ss << std::fixed << std::setprecision(precision) << decimal_value;
            
            
            std::string result = ss.str();
            size_t dot_pos = result.find('.');
            if (dot_pos != std::string::npos) {
                
                size_t last_non_zero = result.find_last_not_of('0');
                if (last_non_zero > dot_pos && last_non_zero < result.length() - 1) {
                    result.erase(last_non_zero + 1);
                }
                
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