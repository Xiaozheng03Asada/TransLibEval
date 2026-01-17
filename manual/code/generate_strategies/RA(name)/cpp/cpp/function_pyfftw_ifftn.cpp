
#include <string>
#include <regex>
#include <stdexcept>
#include <fftw3.h> 

class IFFTNProcessor {
public:

    std::string compute_ifftn(const std::string &input_array) {
    
        std::regex re("\\(\\s*(\\d+\\s*(,\\s*\\d+\\s*)+)\\)");
        std::smatch match;
        if (std::regex_search(input_array, match, re)) {
            return match[0].str();
        }
        throw std::invalid_argument("Error in computing IFFT: invalid input");
    }
};

