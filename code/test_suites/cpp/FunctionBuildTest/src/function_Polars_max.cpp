#include <vector>
#include <algorithm>
#include <optional>

class PolarsExample {
public:
    std::optional<double> compute_max(std::optional<double> x, std::optional<double> y, std::optional<double> z) {
        std::vector<double> values;
        if (x.has_value()) values.push_back(x.value());
        if (y.has_value()) values.push_back(y.value());
        if (z.has_value()) values.push_back(z.value());

        if (values.empty()) {
            return std::nullopt;
        }

        return *std::max_element(values.begin(), values.end());
    }
};