#include <vector>
#include <string>
#include <sstream>
#include <algorithm>

class QuickSortFunction {
public:
    std::string quick_sort_from_string(const std::string& inputStr) {
        if (inputStr.empty()) {
            return "";
        }

        std::vector<int> numbers;
        std::stringstream ss(inputStr);
        std::string token;
        while (std::getline(ss, token, ',')) {
            numbers.push_back(std::stoi(token));
        }

        quickSort(numbers, 0, numbers.size() - 1);

        std::stringstream result;
        for (size_t i = 0; i < numbers.size(); ++i) {
            if (i != 0) {
                result << ",";
            }
            result << numbers[i];
        }

        return result.str();
    }

private:
    void quickSort(std::vector<int>& arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    int partition(std::vector<int>& arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; ++j) {
            if (arr[j] < pivot) {
                ++i;
                std::swap(arr[i], arr[j]);
            }
        }

        std::swap(arr[i + 1], arr[high]);
        return i + 1;
    }
};