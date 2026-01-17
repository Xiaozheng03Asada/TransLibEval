#include <string>
#include <sstream>
#include <boost/multiprecision/cpp_dec_float.hpp>
#include <boost/lexical_cast.hpp>

class GetDecimalTuple {
public:
    std::string check_discount_for_large_order(const std::string& value) {
        try {
            
            boost::multiprecision::cpp_dec_float_50 decimal_value = 
                boost::lexical_cast<boost::multiprecision::cpp_dec_float_50>(value);

            
            int sign = decimal_value < 0 ? 1 : 0;
            decimal_value = abs(decimal_value);

            
            std::string str_value = decimal_value.str(0, std::ios_base::fixed);
            str_value.erase(std::remove(str_value.begin(), str_value.end(), '.'), str_value.end());

            
            while (!str_value.empty() && str_value[0] == '0') {
                str_value.erase(0, 1);
            }
            while (!str_value.empty() && str_value.back() == '0') {
                str_value.pop_back();
            }

            
            if (str_value.empty()) {
                return "0,0,0";
            }

            
            size_t decimal_pos = value.find('.');
            int exponent = 0;
            if (decimal_pos != std::string::npos) {
                exponent = -(value.length() - decimal_pos - 1);
            }

            
            std::stringstream result;
            result << sign << "," << str_value << "," << exponent;
            return result.str();
        }
        catch (const std::exception&) {
            return "Error";
        }
    }
};