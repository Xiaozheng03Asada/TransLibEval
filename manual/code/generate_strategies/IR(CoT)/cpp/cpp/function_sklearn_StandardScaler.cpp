#include <Eigen/Dense>
#include <string>
#include <sstream>
#include <vector>
#include <stdexcept>
#include <algorithm>

class StandardScalerFunction {
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
                row.push_back(std::stod(item));
            }
            data.push_back(row);
        }

        Eigen::MatrixXd matrix(data.size(), data[0].size());
        for (size_t i = 0; i < data.size(); ++i) {
            for (size_t j = 0; j < data[i].size(); ++j) {
                matrix(i, j) = data[i][j];
            }
        }

        
        Eigen::VectorXd mean = matrix.colwise().mean();
        Eigen::MatrixXd centered = matrix.rowwise() - mean.transpose();
        Eigen::VectorXd std_dev = ((centered.array().square().colwise().sum()) / (matrix.rows() - 1)).sqrt();
        Eigen::MatrixXd standardized = centered.array().rowwise() / std_dev.transpose().array();

        std::ostringstream result;
        for (int i = 0; i < standardized.rows(); ++i) {
            for (int j = 0; j < standardized.cols(); ++j) {
                if (j > 0) {
                    result << ",";
                }
                result << standardized(i, j);
            }
            if (i < standardized.rows() - 1) {
                result << ";";
            }
        }

        return result.str();
    }
};