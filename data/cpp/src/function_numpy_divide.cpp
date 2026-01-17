#include <string>
#include <sstream>
#include <optional>
#include <stdexcept>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class DivisionCalculator
{
public:
    template <typename T>
    double divide(const T& arr1,
                  const std::optional<T>& arr2 = std::nullopt,
                  const std::optional<T>& scalar = std::nullopt)
    {
        try
        {
            // 如果 arr2 有值，则执行 arr1 / arr2
            if (arr2.has_value())
            {
                xt::xarray<T> A = {arr1};
                xt::xarray<T> B = {arr2.value()};
                auto result = A / B;  // 计算除法
                return static_cast<double>(result(0));
            }
            // 如果提供了 scalar，则执行 arr1 / scalar
            else if (scalar.has_value())
            {
                xt::xarray<T> A = {arr1};
                xt::xarray<T> S = {scalar.value()};
                auto result = A / S;
                return static_cast<double>(result(0));
            }
            // 否则抛出错误
            else
            {
                throw std::runtime_error("Either arr2 or scalar must be provided.");
            }
        }
        catch(const std::exception& e)
        {
            throw std::runtime_error(std::string("Error in division: ") + e.what());
        }
    }
};