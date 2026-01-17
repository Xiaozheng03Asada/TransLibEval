#include <Eigen/Dense>
#include <string>
#include <sstream>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <unordered_map>
#include <iomanip>

class SimpleImputerFunction {
public:
    static std::string quick_sort_from_string(const std::string& input_str) {
        if (input_str.empty()) {
            return "";
        }

        std::vector<std::vector<double>> data;
        std::istringstream ss(input_str);
        std::string line;

        while (std::getline(ss, line, ';')) {
            std::vector<double> row;
            std::istringstream line_ss(line);
            std::string item;
            while (std::getline(line_ss, item, ',')) {
                if (item == "None") {
                    row.push_back(std::numeric_limits<double>::quiet_NaN());
                } else {
                    row.push_back(std::stod(item));
                }
            }
            data.push_back(row);
        }

        Eigen::MatrixXd matrix(data.size(), data[0].size());
        for (size_t i = 0; i < data.size(); ++i) {
            for (size_t j = 0; j < data[i].size(); ++j) {
                matrix(i, j) = data[i][j];
            }
        }

        // Impute missing values with the most frequent value
        for (int j = 0; j < matrix.cols(); ++j) {
            std::unordered_map<double, int> frequency_map;
            for (int i = 0; i < matrix.rows(); ++i) {
                if (!std::isnan(matrix(i, j))) {
                    frequency_map[matrix(i, j)]++;
                }
            }
            if (!frequency_map.empty()) {
                double most_frequent = std::max_element(frequency_map.begin(), frequency_map.end(),
                                                        [](const std::pair<double, int>& a, const std::pair<double, int>& b) {
                                                            return a.second < b.second;
                                                        })->first;
                for (int i = 0; i < matrix.rows(); ++i) {
                    if (std::isnan(matrix(i, j))) {
                        matrix(i, j) = most_frequent;
                    }
                }
            }
        }

        std::ostringstream result;
        result << std::fixed << std::setprecision(1); // Ensure one decimal place
        for (int i = 0; i < matrix.rows(); ++i) {
            for (int j = 0; j < matrix.cols(); ++j) {
                if (j > 0) {
                    result << ",";
                }
                result << matrix(i, j);
            }
            if (i < matrix.rows() - 1) {
                result << ";";
            }
        }

        return result.str();
    }
};