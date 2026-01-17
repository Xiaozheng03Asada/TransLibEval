#include <string>
#include <stdexcept>
#include <boost/dynamic_bitset.hpp>

class CountBitsFunction {
    
public:
    int count_bits(const std::string& bits, int value) {
        if (value != 0 && value != 1) {
            throw std::invalid_argument("Value must be 0 or 1.");
        }

        if (bits.empty() || bits.find_first_not_of("01") != std::string::npos) {
            throw std::invalid_argument("Input must be a string of '0' and '1'.");
        }

        boost::dynamic_bitset<> bitset(bits);
        return value == 1 ? bitset.count() : bitset.size() - bitset.count();
    }
};