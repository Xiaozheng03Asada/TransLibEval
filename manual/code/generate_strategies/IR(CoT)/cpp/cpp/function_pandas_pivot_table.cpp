#include <string>
#include <sstream>
#include <optional>
#include <xtensor/xarray.hpp>
#include <xtensor/xview.hpp>
#include <xtensor/xadapt.hpp>

class CreatePivotTable {
public:


    template<typename T>
    std::string create_pivot_table(
        const std::optional<std::string>& date1 = std::nullopt,
        const std::optional<std::string>& date2 = std::nullopt,
        const std::optional<std::string>& category1 = std::nullopt,
        const std::optional<std::string>& category2 = std::nullopt,
        const std::optional<T>& value1 = std::nullopt,
        const std::optional<T>& value2 = std::nullopt)
    {
        
        std::string d1 = date1.value_or("2023-01-01");
        std::string c1 = category1.value_or("A");
        std::string c2 = category2.value_or("B");
        T v1 = value1.value_or(static_cast<T>(1));
        T v2 = static_cast<T>(0);  

        
        if (value2.has_value()) {
            v2 = value2.value();
        }

        
        xt::xarray<T> values = {v1, v2};
        std::vector<std::string> categories = {c1, c2};
        
        
        std::ostringstream oss;
        oss << "Date: " << d1 
            << ", Category A: " << (c1 == "A" ? values(0) : 0)
            << ", Category B: " << (c2 == "B" ? values(1) : 0);

        return oss.str();
    }
};