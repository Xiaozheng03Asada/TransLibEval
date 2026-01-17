#include <vector>
#include <optional>
#include <numeric>

class PolarsExample {
public:
    double compute_sum(std::optional<double> x, std::optional<double> y, std::optional<double> z) {
        std::vector<double> values;
        if (x.has_value()) values.push_back(x.value());
        if (y.has_value()) values.push_back(y.value());
        if (z.has_value()) values.push_back(z.value());

        if (values.empty()) {
            return 0.0;
        }

        return std::accumulate(values.begin(), values.end(), 0.0);
    }
};