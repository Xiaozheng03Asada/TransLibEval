#include <Eigen/Dense>
#include <stdexcept>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>

class OneHotEncoderFunction {
public:
    static std::string test_one_hot_encoding(const std::string& data) {
        if (data.empty()) {
            throw std::invalid_argument("Input data must not be empty.");
        }

        std::istringstream iss(data);
        std::string categorical_data;
        int numeric_data;

        if (!(iss >> categorical_data >> numeric_data)) {
            throw std::invalid_argument("The input data has incorrect structure.");
        }

        
        std::unordered_map<std::string, int> category_map = {{"category1", 1}};
        int encoded_value = category_map[categorical_data];

        std::ostringstream oss;
        oss << numeric_data << "," << encoded_value;
        return oss.str();
    }
};