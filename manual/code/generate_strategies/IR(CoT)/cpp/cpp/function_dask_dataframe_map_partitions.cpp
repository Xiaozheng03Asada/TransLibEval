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

            
            int col_index = -1;
            if (!data.empty()) {
                for (size_t i = 0; i < data[0].size(); ++i) {
                    if (data[0][i] == column) {
                        col_index = static_cast<int>(i);
                        break;
                    }
                }
            }

            
            if (col_index == -1) {
                return "Error";
            }

            
            std::vector<double> col_data;
            for (size_t i = 1; i < data.size(); ++i) {
                try {
                    col_data.push_back(std::stod(data[i][col_index]));
                } catch (const std::exception& e) {
                    return "Error";
                }
            }

            
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