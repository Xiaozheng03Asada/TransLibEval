#include <string>
#include <sstream>
#include <iomanip> 
#include <xtensor/xarray.hpp>
#include <xtensor/xsort.hpp>
#include <optional>

class SortCalculator
{
public:

    template <typename T>
    std::string sort(const std::optional<T>& value1 = std::nullopt,
                     const std::optional<T>& value2 = std::nullopt,
                     const std::optional<T>& value3 = std::nullopt)
    {
        T v1, v2, v3;
        if (!value1.has_value() || !value2.has_value() || !value3.has_value())
        {
            v1 = static_cast<T>(10);
            v2 = static_cast<T>(5);
            v3 = static_cast<T>(15);
        }
        else
        {
            v1 = value1.value();
            v2 = value2.value();
            v3 = value3.value();
        }

        xt::xarray<T> data = { v1, v2, v3 };
        auto sorted_data = xt::sort(data);

        std::ostringstream oss;
        
        oss << std::fixed << std::setprecision(1)
            << "Sorted Values: "
            << sorted_data(0) << ", "
            << sorted_data(1) << ", "
            << sorted_data(2);

        return oss.str();
    }
};