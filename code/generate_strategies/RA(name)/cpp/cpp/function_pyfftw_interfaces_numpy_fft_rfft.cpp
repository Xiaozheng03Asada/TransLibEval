#include <string>
#include <regex>
#include <stdexcept>
#include <sstream>
#include <vector>
#include <fftw3.h>  

class RFFTProcessor {
public:

    std::string compute_rfft(const std::string &input_array) {
        try {
            std::regex re_array(R"(np\.array\s*\(\s*\[(.*)\]\s*\))");
            std::regex re_zeros(R"(np\.zeros\s*\(\s*(\d+)\s*\))");
            std::regex re_random(R"(np\.random\.random\s*\(\s*(\d+)\s*\))");
            std::smatch match;

            if (std::regex_search(input_array, match, re_array)) {
                std::string inside = match[1].str();
                std::regex re_comma(R"(\s*,\s*)");
                std::sregex_token_iterator iter(inside.begin(), inside.end(), re_comma, -1);
                std::sregex_token_iterator end;
                int count = 0;
                for (; iter != end; ++iter) {
                    std::string token = iter->str();
                    try {
                        std::stod(token);  
                        count++;
                    } catch (...) {
                        throw std::invalid_argument("Input array must contain numeric data.");
                    }
                }
                if (count == 0) {
                    throw std::invalid_argument("Input array is empty.");
                }
                return "(" + std::to_string(count / 2 + 1) + ",)";
            }

            if (std::regex_search(input_array, match, re_zeros)) {
                int count = std::stoi(match[1].str());
                if (count <= 0) {
                    throw std::invalid_argument("Array size must be greater than 0.");
                }
                return "(" + std::to_string(count / 2 + 1) + ",)";
            }

            if (std::regex_search(input_array, match, re_random)) {
                int count = std::stoi(match[1].str());
                if (count <= 0) {
                    throw std::invalid_argument("Array size must be greater than 0.");
                }
                return "(" + std::to_string(count / 2 + 1) + ",)";
            }

            throw std::invalid_argument("Invalid input format.");
        } catch (const std::exception &e) {
            throw std::invalid_argument(std::string("Error in computing RFFT: ") + e.what());
        }
    }
};