#include <string>
#include <optional>
#include <vector>
#include <numeric>

class CalculateMean {
public:
    std::string calculate_mean(std::optional<double> value_A, std::optional<double> value_B) {
        std::optional<double> mean_A;
        std::optional<double> mean_B;

        if (!value_A.has_value() || !value_B.has_value()) {
            mean_A = std::nullopt;
            mean_B = std::nullopt;
        } else {
            std::vector<double> column_A = {value_A.value()};
            std::vector<double> column_B = {value_B.value()};

            mean_A = std::accumulate(column_A.begin(), column_A.end(), 0.0) / column_A.size();
            mean_B = std::accumulate(column_B.begin(), column_B.end(), 0.0) / column_B.size();
        }

        return "A: " + (mean_A.has_value() ? std::to_string(mean_A.value()) : "None") +
               ", B: " + (mean_B.has_value() ? std::to_string(mean_B.value()) : "None");
    }
};