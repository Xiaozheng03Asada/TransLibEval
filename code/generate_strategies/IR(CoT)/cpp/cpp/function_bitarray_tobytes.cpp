#include <string>
#include <stdexcept>
#include <boost/dynamic_bitset.hpp>
#include <sstream>
#include <iomanip>
#include <algorithm>

class ConvertToBytesFunction {
public:
    std::string convert_to_bytes(const std::string& bits_str) {
        if (bits_str.find_first_not_of("01") != std::string::npos) {
            throw std::invalid_argument("Input must be a binary string consisting of '0' and '1'.");
        }
        if (bits_str.empty()) {
            return "b''";
        }
        std::string reversed_bits(bits_str);
        std::reverse(reversed_bits.begin(), reversed_bits.end());
        boost::dynamic_bitset<> bitset(reversed_bits);
        size_t num_bits = bits_str.length();
        size_t num_bytes = (num_bits + 7) / 8;
        std::vector<unsigned char> bytes(num_bytes, 0);
        boost::to_block_range(bitset, bytes.begin());
        std::reverse(bytes.begin(), bytes.end());
        for (auto& byte : bytes) { 
            byte = (byte & 0xF0) >> 4 | (byte & 0x0F) << 4;
            byte = (byte & 0xCC) >> 2 | (byte & 0x33) << 2;
            byte = (byte & 0xAA) >> 1 | (byte & 0x55) << 1;
        }
        std::ostringstream result;
        result << "b'";
        for (unsigned char byte : bytes) {
            result << "\\x" << std::hex << std::setw(2) << std::setfill('0') << static_cast<int>(byte);
        }
        result << "'";
        return result.str();
    }
};