
#include <boost/optional.hpp>
#include <vector>
#include <algorithm>
#include <cmath>

class PolarsExample {
public:
    boost::optional<float> filter_numbers(float threshold, float x, 
                                        float y = std::numeric_limits<float>::quiet_NaN(),
                                        float z = std::numeric_limits<float>::quiet_NaN()) {
        std::vector<float> values;
        values.push_back(x);
        
        if (!std::isnan(y)) values.push_back(y);
        if (!std::isnan(z)) values.push_back(z);

        if (values.empty()) {
            return boost::none;
        }

        
        std::vector<float> filtered;
        std::copy_if(values.begin(), values.end(), 
                    std::back_inserter(filtered),
                    [threshold](float val) { return val > threshold; });

        if (filtered.empty()) {
            return boost::none;
        }

        
        return *std::max_element(filtered.begin(), filtered.end());
    }
};