#include <string>
#include <openssl/md5.h>
#include <sstream>
#include <iomanip>

class HashingExample {
public:
    std::string compute_hash(const std::string& input_data) {
        std::string inputStr = input_data.empty() ? "null" : input_data;
        unsigned char digest[MD5_DIGEST_LENGTH];
        MD5((unsigned char*)inputStr.c_str(), inputStr.size(), digest);

        std::stringstream ss;
        for(int i = 0; i < MD5_DIGEST_LENGTH; ++i) {
            ss << std::hex << std::setw(2) << std::setfill('0') << (int)digest[i];
        }
        return ss.str();
    }
};