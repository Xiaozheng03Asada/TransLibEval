#include <string>
#include <sstream>
#include <vector>
#include <numeric>
#include <iomanip>
#include <Eigen/Dense>

class ComputePartitionMeansFunction {
public:
    std::string compute_partition_means(const std::string& data_str, const std::string& column) {
        try {
            // 解析 CSV 数据
            std::vector<std::vector<std::string>> data;
            std::stringstream ss(data_str);
            std::string line;
            while (std::getline(ss, line)) {
                std::vector<std::string> row;
                std::stringstream line_ss(line);
                std::string cell;
                while (std::getline(line_ss, cell, ',')) {
                    row.push_back(cell);
                }
                data.push_back(row);
            }

            // 获取列索引
            int col_index = -1;
            if (!data.empty()) {
                for (size_t i = 0; i < data[0].size(); ++i) {
                    if (data[0][i] == column) {
                        col_index = static_cast<int>(i);
                        break;
                    }
                }
            }

            // 检查列是否存在
            if (col_index == -1) {
                return "Error";
            }

            // 提取数据并计算均值
            std::vector<double> col_data;
            for (size_t i = 1; i < data.size(); ++i) {
                try {
                    col_data.push_back(std::stod(data[i][col_index]));
                } catch (const std::exception& e) {
                    return "Error";
                }
            }

            // 分块计算均值
            int npartitions = 2;
            int partition_size = (col_data.size() + npartitions - 1) / npartitions;
            std::vector<double> partition_means;
            for (int i = 0; i < npartitions; ++i) {
                int start = i * partition_size;
                int end = std::min(start + partition_size, static_cast<int>(col_data.size()));
                if (start < end) {
                    Eigen::Map<Eigen::VectorXd> partition_data(col_data.data() + start, end - start);
                    partition_means.push_back(partition_data.mean());
                }
            }

            // 格式化输出
            std::stringstream result_ss;
            for (size_t i = 0; i < partition_means.size(); ++i) {
                result_ss << std::fixed << std::setprecision(1) << partition_means[i];
                if (i < partition_means.size() - 1) {
                    result_ss << ",";
                }
            }
            return result_ss.str();
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};