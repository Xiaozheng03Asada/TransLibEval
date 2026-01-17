#include <string>
#include <stdexcept>
#include <boost/dynamic_bitset.hpp>
#include <algorithm>

class BitarrayExtender
{
public:
    std::string extend_bits(const std::string &bits, const std::string &values)
    {
        
        if (bits.find_first_not_of("01") != std::string::npos || values.find_first_not_of("01") != std::string::npos)
        {
            throw std::invalid_argument("All elements in the input must be '0' or '1'.");
        }

        
        size_t total_length = bits.length() + values.length();

        
        boost::dynamic_bitset<> bitset(total_length);

        
        for (size_t i = 0; i < bits.length(); ++i)
        {
            if (bits[i] == '1')
            {
                bitset[total_length - 1 - i] = 1;
            }
            else
            {
                bitset[total_length - 1 - i] = 0;
            }
        }

        for (size_t i = 0; i < values.length(); ++i)
        {
            if (values[i] == '1')
            {
                bitset[values.length() - 1 - i] = 1;
            }
            else
            {
                bitset[values.length() - 1 - i] = 0;
            }
        }

        
        std::string result;
        for (size_t i = total_length; i > 0; --i)
        {
            result += (bitset[total_length - i] ? '1' : '0');
        }
        std::reverse(result.begin(), result.end());
        return result;
    }
};