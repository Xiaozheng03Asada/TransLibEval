#include <string>
#include <sstream>
#include <vector>
#include <numeric>
#include <iomanip>
#include <Eigen/Dense>

class CalculateMeanFunction {
public:
    std::string calculate_mean(const std::string& data_str, int chunks) {
        try {
            
            std::vector<double> data;
            std::stringstream ss(data_str);
            std::string item;
            while (std::getline(ss, item, ',')) {
                data.push_back(std::stod(item));
            }

            
            if (data.empty()) {
                return "Error: Input data is empty";
            }

            
            int block_size = data.size() / chunks;
            if (data.size() % chunks != 0) {
                block_size++;
            }

            
            Eigen::VectorXd eigen_data = Eigen::Map<Eigen::VectorXd, Eigen::Unaligned>(data.data(), data.size());
            double mean = eigen_data.mean();

            
            std::stringstream result_ss;
            result_ss << std::fixed << std::setprecision(1) << mean;
            return result_ss.str();
        } catch (const std::exception& e) {
            return "Error: " + std::string(e.what());
        }
    }
};