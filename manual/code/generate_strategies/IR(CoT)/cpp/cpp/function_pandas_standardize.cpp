#include <string>
#include <sstream>
#include <optional>
#include <iomanip>
#include <cmath>
#include <limits>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class StandardizeData
{
public:

   
    template <typename T>
    std::string standardize(const std::optional<T>& value_A = std::nullopt,
                              const std::optional<T>& value_B = std::nullopt)
    {
        std::ostringstream oss;
        
        if (!value_A.has_value() || !value_B.has_value())
        {
            oss << "A: None, B: None";
            return oss.str();
        }

        
        xt::xarray<double> arrA = {static_cast<double>(value_A.value())};
        xt::xarray<double> arrB = {static_cast<double>(value_B.value())};

        
        double mean_A = arrA(0);
        double mean_B = arrB(0);

        
        double std_A = std::numeric_limits<double>::quiet_NaN();
        double std_B = std::numeric_limits<double>::quiet_NaN();

        
        double standardized_A = ((!std::isnan(std_A)) && std_A != 0) ? (static_cast<double>(value_A.value()) - mean_A) / std_A : 0;
        double standardized_B = ((!std::isnan(std_B)) && std_B != 0) ? (static_cast<double>(value_B.value()) - mean_B) / std_B : 0;

        
        oss << std::fixed << std::setprecision(1)
            << "A: " << standardized_A << ", B: " << standardized_B;

        return oss.str();
    }
};