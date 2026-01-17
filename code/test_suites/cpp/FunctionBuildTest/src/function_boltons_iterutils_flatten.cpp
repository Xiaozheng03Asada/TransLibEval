#include <string>
#include <vector>
#include <sstream>
#include <algorithm>
#include <iterator>

class DataFlattener {
public:
    std::string flatten_data(const std::string& data_str) {
        try {
            std::vector<std::vector<int>> data;
            std::istringstream iss(data_str);
            std::string part;
            while (std::getline(iss, part, ';')) {
                if (part.empty()) {
                    throw std::invalid_argument("Invalid input");
                }
                std::vector<int> row;
                std::istringstream iss_row(part);
                std::string numStr;
                while (std::getline(iss_row, numStr, ',')) {
                    row.push_back(std::stoi(numStr));
                }
                data.push_back(row);
            }
            std::vector<int> flattened;
            for (const auto& row : data) {
                flattened.insert(flattened.end(), row.begin(), row.end());
            }
            std::ostringstream oss;
            bool first = true;
            for (int num : flattened) {
                if (!first) {
                    oss << ",";
                }
                first = false;
                oss << num;
            }
            return oss.str();
        } catch (const std::exception& e) {
            return "Error";
        }
    }
};