#include <string>
#include <sstream>
#include <optional>
#include <limits>
#include <cmath>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class FillMissingValues
{
public:


    template <typename T>
    std::string fill_missing_values(const std::optional<T>& value_A,
                                    const std::optional<T>& value_B)
    {
       
        if (!value_A.has_value() && !value_B.has_value())
        {
            return "A: nan, B: nan";
        }

      
        T a = value_A.value_or(std::numeric_limits<T>::quiet_NaN());
        T b = value_B.value_or(std::numeric_limits<T>::quiet_NaN());

        // 组装字符串输出
        std::ostringstream oss;
        oss << "A: ";
        if (std::isnan(static_cast<double>(a))) { oss << "nan"; }
        else { oss << a; }

        oss << ", B: ";
        if (std::isnan(static_cast<double>(b))) { oss << "nan"; }
        else { oss << b; }

        return oss.str();
    }
};