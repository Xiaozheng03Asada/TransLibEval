#include <string>
#include <boost/dynamic_bitset.hpp>
#include <stdexcept>
class InvertBitsFunction {
public:
    std::string invert_bitarray(const std::string& bits, const std::string& start = "-1", const std::string& stop = "-1") {
        // 验证输入字符串
        if (bits.empty() || bits.find_first_not_of("01") != std::string::npos) {
            throw std::invalid_argument("Input must be a string containing only '0' and '1'.");
        }

        // 将字符串转换为整数并验证
        int start_idx, stop_idx;
        try {
            size_t pos;
            start_idx = std::stoi(start, &pos);
            if (pos != start.length()) {
                throw std::invalid_argument("Start index must be an integer.");
            }
            
            stop_idx = std::stoi(stop, &pos);
            if (pos != stop.length()) {
                throw std::invalid_argument("Stop index must be an integer.");
            }
        } catch (const std::exception&) {
            throw std::invalid_argument("Invalid start or stop index format.");
        }

        // 验证索引
        if (start_idx != -1 && stop_idx != -1) {
            if (start_idx < 0 || stop_idx > bits.length() || start_idx >= stop_idx) {
                throw std::invalid_argument("Invalid start and stop indices.");
            }
        }

        // 创建 bitset
        boost::dynamic_bitset<> bit_array(bits.length());
        for (size_t i = 0; i < bits.length(); ++i) {
            bit_array[bits.length() - 1 - i] = (bits[i] == '1');
        }

        // 执行反转操作
        if (start_idx == -1 && stop_idx == -1) {
            bit_array.flip();
        } else {
            for (int i = start_idx; i < stop_idx; ++i) {
                bit_array.flip(bits.length() - 1 - i);
            }
        }

        // 转换回字符串
        std::string result;
        for (size_t i = 0; i < bit_array.size(); ++i) {
            result = (bit_array[i] ? '1' : '0') + result;
        }

        return result;
    }
};