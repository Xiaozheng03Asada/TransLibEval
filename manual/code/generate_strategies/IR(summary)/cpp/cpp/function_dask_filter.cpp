#include <string>
#include <sstream>
#include <vector>
#include <numeric>
#include <iomanip>
#include <Eigen/Dense>

class FilterEvenNumbersDask {
public:
    std::string check_discount_for_large_order(const std::string& data_str) {
        try {
            if (data_str.empty()) {
                return "";
            }

            std::vector<int> data;
            std::stringstream ss(data_str);
            std::string item;
            while (std::getline(ss, item, ',')) {
                try {
                    data.push_back(std::stoi(item));
                } catch (const std::exception& e) {
                    return "Error";
                }
            }

            std::vector<int> even_numbers;
            for (int num : data) {
                if (num % 2 == 0) {
                    even_numbers.push_back(num);
                }
            }

            std::stringstream result_ss;
            for (size_t i = 0; i < even_numbers.size(); ++i) {
                result_ss << even_numbers[i];
                if (i < even_numbers.size() - 1) {
                    result_ss << ",";
                }
            }
            return result_ss.str();
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};