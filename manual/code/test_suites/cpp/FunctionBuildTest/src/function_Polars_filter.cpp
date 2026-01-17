#include <vector>
#include <algorithm>
#include <cmath>
#include <limits>

class PolarsExample {
public:
    float filter_numbers(float threshold, float x, float* y, float* z) {
        std::vector<float> valuesList;
        valuesList.push_back(x);
        if (y != nullptr) valuesList.push_back(*y);
        if (z != nullptr) valuesList.push_back(*z);

        if (valuesList.empty()) {
            return std::numeric_limits<float>::quiet_NaN();
        }

        std::vector<double> values;
        for (float val : valuesList) {
            if (val > threshold) {
                values.push_back(static_cast<double>(val));
            }
        }

        if (values.empty()) {
            return std::numeric_limits<float>::quiet_NaN();
        }

        double maxValue = *std::max_element(values.begin(), values.end());

        return static_cast<float>(maxValue);
    }
};