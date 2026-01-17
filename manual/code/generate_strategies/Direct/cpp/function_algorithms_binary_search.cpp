#include <Eigen/Dense>
#include <string>

class BinarySearchFunction {
public:
    template<typename T>
    int binary_search_index(const std::string& sorted_string, const T& target) {
        if (sorted_string.empty()) {
            return -1;
        }
        
        Eigen::VectorXi vec(sorted_string.size());
        for (size_t i = 0; i < sorted_string.size(); ++i) {
            vec(i) = sorted_string[i];
        }
        
        int left = 0;
        int right = vec.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (vec(mid) == static_cast<int>(target)) {
                return mid;
            }
            if (vec(mid) < static_cast<int>(target)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
};