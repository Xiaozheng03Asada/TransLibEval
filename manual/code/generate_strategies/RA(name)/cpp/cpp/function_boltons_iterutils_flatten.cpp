#include <range/v3/all.hpp>
#include <string>
#include <sstream>
#include <vector>
#include <stdexcept>
#include <type_traits>

class DataFlattener {
public:
    template<typename T>
    std::string flatten_data(const T &data) {
        if constexpr (std::is_same_v<T, std::string>) {
            try {
                if (data.empty()) {
                    return "Error";
                }
                // 按分号拆分各部分
                std::vector<std::string> parts;
                {
                    std::istringstream iss(data);
                    std::string part;
                    while (std::getline(iss, part, ';')) {
                        parts.push_back(part);
                    }
                }
                // 对每个部分按逗号拆分并转换为整数形成嵌套 vector
                std::vector<std::vector<int>> nested;
                for (const auto &p : parts) {
                    std::vector<int> nums;
                    std::istringstream iss_part(p);
                    std::string num_str;
                    while (std::getline(iss_part, num_str, ',')) {
                        if (!num_str.empty())
                            nums.push_back(std::stoi(num_str));
                    }
                    nested.push_back(nums);
                }
                // 使用 range-v3 的 views::join 扁平化嵌套 vector
                auto flattened_view = nested | ranges::views::join;
                std::vector<int> flat = flattened_view | ranges::to_vector;
                // 拼接扁平化后的整数为字符串，逗号分隔
                std::ostringstream oss;
                bool first = true;
                for (int num : flat) {
                    if (!first)
                        oss << ",";
                    oss << num;
                    first = false;
                }
                return oss.str();
            } catch (...) {
                return "Error";
            }
        } else {
            return "Error";
        }
    }
};