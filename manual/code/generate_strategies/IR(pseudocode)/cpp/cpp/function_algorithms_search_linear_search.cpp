#include <Eigen/Dense>
#include <string>
#include <sstream>

class LinearSearchFunction {
public:
    template<typename T>
    int linear_search_from_string(const std::string& arr, const T& target) {
        if (arr.empty()) {
            return -1;
        }

        std::vector<int> numbers;
        std::stringstream ss(arr);
        std::string item;
        
        while (std::getline(ss, item, ',')) {
            numbers.push_back(std::stoi(item));
        }
        
        Eigen::VectorXi vec = Eigen::Map<Eigen::VectorXi>(numbers.data(), numbers.size());
        
        for (int i = 0; i < vec.size(); i++) {
            if (vec(i) == static_cast<int>(target)) {
                return i;
            }
        }
        
        return -1;
    }
};