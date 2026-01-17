#include <Eigen/Dense>
#include <string>
#include <sstream>
#include <stack>

class QuickSortFunction {
public:
    std::string quick_sort_from_string(const std::string& input_str) {
        if (input_str.empty()) {
            return "";
        }

        
        std::vector<int> numbers;
        std::stringstream ss(input_str);
        std::string item;
        while (std::getline(ss, item, ',')) {
            numbers.push_back(std::stoi(item));
        }

        
        Eigen::VectorXi arr = Eigen::Map<Eigen::VectorXi>(numbers.data(), numbers.size());
        
        
        std::stack<std::pair<int, int>> st;
        st.push({0, arr.size() - 1});

        while (!st.empty()) {
            int low = st.top().first;
            int high = st.top().second;
            st.pop();

            if (low < high) {
                
                int pivot = arr(high);
                int i = low - 1;

                for (int j = low; j <= high - 1; j++) {
                    if (arr(j) < pivot) {
                        i++;
                        std::swap(arr(i), arr(j));
                    }
                }
                std::swap(arr(i + 1), arr(high));
                int pi = i + 1;

                
                st.push({pi + 1, high});
                st.push({low, pi - 1});
            }
        }

        
        std::stringstream result;
        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) result << ",";
            result << arr(i);
        }
        return result.str();
    }
};