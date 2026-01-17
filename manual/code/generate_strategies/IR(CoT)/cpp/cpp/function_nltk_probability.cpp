#include <string>
#include <boost/any.hpp>
#include <fmt/format.h>
#include <fmt/ranges.h>
#include <vector>
#include <utility>

class ProbabilityExample {
    public:
        std::string compute_frequency(const boost::any& data) {
            try {
                
                std::string input;
                if (data.type() == typeid(std::string)) {
                    input = boost::any_cast<std::string>(data);
                }
                else if (data.type() == typeid(const char*)) {
                    input = boost::any_cast<const char*>(data);
                }
                else {
                    throw std::invalid_argument("Input must be string");
                }
    
                if (input.empty()) {
                    return "";
                }
    
                
                std::vector<std::pair<char, int>> freq_dist;
                for (char c : input) {
                    
                    bool found = false;
                    for (auto& pair : freq_dist) {
                        if (pair.first == c) {
                            pair.second++;
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        freq_dist.emplace_back(c, 1);
                    }
                }
    
                
                std::string result;
                bool first = true;
                for (const auto& [key, value] : freq_dist) {
                    if (!first) {
                        result += ", ";
                    }
                    result += fmt::format("{}:{}", key, value);
                    first = false;
                }
    
                return result;
            }
            catch (const boost::bad_any_cast&) {
                throw std::invalid_argument("Input must be string");
            }
        }
    };
