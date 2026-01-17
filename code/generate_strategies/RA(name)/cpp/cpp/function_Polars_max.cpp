#include <boost/optional.hpp>
#include <vector>
#include <algorithm>

class PolarsExample {
public:
    float compute_max(float x, 
                     const boost::optional<float>& y = boost::none,
                     const boost::optional<float>& z = boost::none) {
        std::vector<float> values;
        values.push_back(x);
        
        if (y) values.push_back(*y);
        if (z) values.push_back(*z);

        return *std::max_element(values.begin(), values.end());
    }
};