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
            // 将字符串转换为浮点数向量
            std::vector<double> input_list;
            std::stringstream ss(input_str);
            std::string item;
            while (std::getline(ss, item, ',')) {
                input_list.push_back(std::stod(item));
            }

            // 使用 Eigen 库计算累积和
            Eigen::VectorXd eigen_data = Eigen::Map<Eigen::VectorXd, Eigen::Unaligned>(input_list.data(), input_list.size());
            double result = eigen_data.sum();

            // 格式化输出
            std::stringstream result_ss;
            result_ss << std::fixed << std::setprecision(1) << result;
            return result_ss.str();
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};