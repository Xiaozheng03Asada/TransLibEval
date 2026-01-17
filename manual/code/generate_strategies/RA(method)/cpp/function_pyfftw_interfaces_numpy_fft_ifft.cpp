#include <string>
#include <regex>
#include <stdexcept>
#include <sstream>
#include <fftw3.h> 

class IFFTProcessor {
public:

    std::string compute_ifft(const std::string &input_array) {
        try {
            if ( input_array.find("j") == std::string::npos &&
                 input_array.find("dtype=complex") == std::string::npos )
            {
                throw std::invalid_argument("Input array must contain complex numbers.");
            }
            std::smatch match;
            std::regex re;
            std::ostringstream oss;
            re = std::regex("np\\.array\\s*\\(\\s*\\[(.*)\\]\\s*\\)");
            if (std::regex_search(input_array, match, re)) {
                std::string inside = match[1].str();
                std::regex comma_re("\\s*,\\s*");
                std::sregex_token_iterator iter(inside.begin(), inside.end(), comma_re, -1);
                std::sregex_token_iterator end;
                int count = 0;
                for (; iter != end; ++iter) {
                    std::string token = iter->str();
                    if (!token.empty())
                        count++;
                }
                oss << "(" << count << ",)";
                return oss.str();
            }
            re = std::regex("np\\.zeros\\s*\\(\\s*(\\d+)");
            if (std::regex_search(input_array, match, re)) {
                std::string count = match[1].str();
                return "(" + count + ",)";
            }
            re = std::regex("np\\.random\\.random\\s*\\(\\s*(\\d+)\\s*\\)");
            if (std::regex_search(input_array, match, re)) {
                std::string count = match[1].str();
                return "(" + count + ",)";
            }
            throw std::invalid_argument("Error in computing IFFT: invalid input");
        } catch (std::exception &e) {
            throw std::invalid_argument(std::string("Error in computing IFFT: ") + e.what());
        }
    }
};