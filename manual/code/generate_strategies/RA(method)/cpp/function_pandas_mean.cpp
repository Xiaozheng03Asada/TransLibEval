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
        // 当任一值缺失时，返回 "A: None, B: None"
        if (!value_A.has_value() || !value_B.has_value())
        {
            oss << "A: None, B: None";
            return oss.str();
        }

        // 利用 xtensor 构造单元素数组模拟 DataFrame 列
        xt::xarray<double> arrA = { static_cast<double>(value_A.value()) };
        xt::xarray<double> arrB = { static_cast<double>(value_B.value()) };

        // 单行数组的均值即为本身
        double mean_A = arrA(0);
        double mean_B = arrB(0);

        // 保持 Python 输出格式（浮点数固定 1 位小数）
        oss << std::fixed << std::setprecision(1)
            << "A: " << mean_A << ", B: " << mean_B;

        return oss.str();
    }
};