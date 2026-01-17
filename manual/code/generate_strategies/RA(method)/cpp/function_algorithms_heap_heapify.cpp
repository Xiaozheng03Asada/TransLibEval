#include <Eigen/Dense>
#include <string>
#include <sstream>

class HeapifyFunction {
public:
    std::string heapify_from_string(const std::string& arr_str) {
        if (arr_str.empty()) return "";
        
        // 解析输入字符串
        std::vector<int> numbers;
        std::stringstream ss(arr_str);
        std::string item;
        while (std::getline(ss, item, ',')) {
            numbers.push_back(std::stoi(item));
        }
        
        // 转换为 Eigen 向量
        Eigen::VectorXi vec = Eigen::Map<Eigen::VectorXi>(numbers.data(), numbers.size());
        
        // 堆化
        for (int i = vec.size() / 2 - 1; i >= 0; i--) {
            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < vec.size() && vec(left) < vec(smallest))
                smallest = left;

            if (right < vec.size() && vec(right) < vec(smallest))
                smallest = right;

            if (smallest != i) {
                int temp = vec(i);
                vec(i) = vec(smallest);
                vec(smallest) = temp;
                
                // 递归部分转换为迭代
                i = smallest + 1; // 重新检查受影响的子树
            }
        }
        
        // 转换回字符串
        std::stringstream result;
        for (int i = 0; i < vec.size(); i++) {
            if (i > 0) result << ",";
            result << vec(i);
        }
        
        return result.str();
    }
};