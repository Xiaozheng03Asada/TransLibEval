#include <string>
#include <sstream>
#include <optional>
#include <iomanip>
#include <limits>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class CalculateMean
{
public:


    template <typename T>
    std::string calculate_mean(const std::optional<T>& value_A = std::nullopt,
                                 const std::optional<T>& value_B = std::nullopt)
    {
        std::ostringstream oss;
        
        if (!value_A.has_value() || !value_B.has_value())
        {
            oss << "A: None, B: None";
            return oss.str();
        }

        
        xt::xarray<double> arrA = { static_cast<double>(value_A.value()) };
        xt::xarray<double> arrB = { static_cast<double>(value_B.value()) };

        
        double mean_A = arrA(0);
        double mean_B = arrB(0);

        
        oss << std::fixed << std::setprecision(1)
            << "A: " << mean_A << ", B: " << mean_B;

        return oss.str();
    }
};