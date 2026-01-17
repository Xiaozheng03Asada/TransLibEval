#include <string>
#include <sstream>
#include <vector>
#include <numeric>
#include <iomanip>
#include <Eigen/Dense>

class CumulativeSumFunction {
public:
    std::string cumulative_sum(const std::string& input_str) {
        try {
            
            std::vector<double> input_list;
            std::stringstream ss(input_str);
            std::string item;
            while (std::getline(ss, item, ',')) {
                input_list.push_back(std::stod(item));
            }

            
            Eigen::VectorXd eigen_data = Eigen::Map<Eigen::VectorXd, Eigen::Unaligned>(input_list.data(), input_list.size());
            double result = eigen_data.sum();

            
            std::stringstream result_ss;
            result_ss << std::fixed << std::setprecision(1) << result;
            return result_ss.str();
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};