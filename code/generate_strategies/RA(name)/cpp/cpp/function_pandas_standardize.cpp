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
        // 当任一值缺失时，返回 "A: None, B: None"
        if (!value_A.has_value() || !value_B.has_value())
        {
            oss << "A: None, B: None";
            return oss.str();
        }

        // 利用 xtensor 构造单元素数组模拟 DataFrame 列
        xt::xarray<double> arrA = {static_cast<double>(value_A.value())};
        xt::xarray<double> arrB = {static_cast<double>(value_B.value())};

        // 单行数组均值即为本身
        double mean_A = arrA(0);
        double mean_B = arrB(0);

        // 模拟单行数据的标准差（pandas计算单个样本std为NaN）
        double std_A = std::numeric_limits<double>::quiet_NaN();
        double std_B = std::numeric_limits<double>::quiet_NaN();

        // 计算标准化值：若std为NaN（或0），则按 Python 逻辑返回0
        double standardized_A = ((!std::isnan(std_A)) && std_A != 0) ? (static_cast<double>(value_A.value()) - mean_A) / std_A : 0;
        double standardized_B = ((!std::isnan(std_B)) && std_B != 0) ? (static_cast<double>(value_B.value()) - mean_B) / std_B : 0;

        // 保持 Python 输出格式（浮点数固定 1 位小数）
        oss << std::fixed << std::setprecision(1)
            << "A: " << standardized_A << ", B: " << standardized_B;

        return oss.str();
    }
};