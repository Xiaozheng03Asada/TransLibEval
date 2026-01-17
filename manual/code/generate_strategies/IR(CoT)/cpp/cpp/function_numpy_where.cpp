#include <string>
#include <xtensor/xarray.hpp>
#include <xtensor/xio.hpp>

class NumpyExample
{
public:

    template <typename T>
    std::string check_number(const T& x)
    {
        
        xt::xarray<T> arr = {x};

        
        auto pos_mask = xt::where(arr > 0, xt::xarray<int>({1}), xt::xarray<int>({0}));
        auto neg_mask = xt::where(arr < 0, xt::xarray<int>({1}), xt::xarray<int>({0}));

        
        if (pos_mask(0) == 1) return "positive";
        if (neg_mask(0) == 1) return "negative";
        return "zero";
    }
};