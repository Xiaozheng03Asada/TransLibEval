#include <string>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class NumpyExample
{
public:

    template <typename T>
    std::string check_number(const T& x)
    {
        // 构造单元素 xarray
        xt::xarray<T> arr = {x};

        // 计算条件掩码
        auto pos_mask = xt::where(arr > 0, xt::xarray<int>({1}), xt::xarray<int>({0}));
        auto neg_mask = xt::where(arr < 0, xt::xarray<int>({1}), xt::xarray<int>({0}));

        // 根据掩码判断最终值
        if (pos_mask(0) == 1) return "positive";
        if (neg_mask(0) == 1) return "negative";
        return "zero";
    }
};