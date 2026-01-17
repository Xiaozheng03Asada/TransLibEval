#include <string>
#include <cmath>
#include <iomanip>
#include <sstream>

class FillMissingValues {
public:
    std::string fill_missing_values(double value_A, double value_B) {
        if (std::isnan(value_A) && std::isnan(value_B)) {
            return "A: nan, B: nan";
        } else {
            double meanA = std::isnan(value_A) ? NAN : value_A;
            double meanB = std::isnan(value_B) ? NAN : value_B;

            double finalA = std::isnan(meanA) ? NAN : meanA;
            double finalB = std::isnan(meanB) ? NAN : meanB;

            std::string resultA = std::isnan(finalA) ? "nan" : std::to_string(finalA);
            std::string resultB = std::isnan(finalB) ? "nan" : std::to_string(finalB);

            std::ostringstream oss;
            oss << "A: " << resultA << ", B: " << resultB;
            return oss.str();
        }
    }
};