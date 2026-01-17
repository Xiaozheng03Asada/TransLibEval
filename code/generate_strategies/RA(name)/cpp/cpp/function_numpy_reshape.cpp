#include <string>
#include <sstream>
#include <xtensor/xarray.hpp>
#include <xtensor/xview.hpp>
#include <optional>

class ReshapeCalculator
{
public:

    template <typename T>
    std::string reshape(const std::optional<T>& price = std::nullopt,
                        const std::optional<T>& quantity = std::nullopt)
    {
        T p, q;
        if (!price.has_value() || !quantity.has_value())
        {
            p = static_cast<T>(10);
            q = static_cast<T>(5);
        }
        else
        {
            p = price.value();
            q = quantity.value();
        }

        xt::xarray<T> data = { p, q };
        auto reshaped_data = xt::reshape_view(data, {1, 2});
        T total_amount = reshaped_data(0, 0) * reshaped_data(0, 1);

        std::ostringstream oss;
        oss << "Price: " << reshaped_data(0, 0)
            << ", Quantity: " << reshaped_data(0, 1)
            << ", Total Amount: " << total_amount;

        return oss.str();
    }
};