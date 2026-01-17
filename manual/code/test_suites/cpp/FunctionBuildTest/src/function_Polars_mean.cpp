#include <vector>
#include <algorithm>
#include <numeric>
#include <cmath>
#include <optional>

class PolarsExample {
public:
    std::optional<double> compute_mean(std::optional<float> x, std::optional<float> y, std::optional<float> z) {
        std::vector<double> values;

        if (x.has_value()) values.push_back(x.value());
        if (y.has_value()) values.push_back(y.value());
        if (z.has_value()) values.push_back(z.value());

        if (values.empty()) {
            return std::nullopt;
        }

        double sum = std::accumulate(values.begin(), values.end(), 0.0);
        return sum / values.size();
    }
};