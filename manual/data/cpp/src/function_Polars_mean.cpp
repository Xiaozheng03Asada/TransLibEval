#include <boost/optional.hpp>
#include <vector>
#include <numeric>

class PolarsExample {
public:
    boost::optional<float> compute_mean(float x, 
                                      const boost::optional<float>& y = boost::none,
                                      const boost::optional<float>& z = boost::none) {
        std::vector<float> values;
        values.push_back(x);
        
        if (y) values.push_back(*y);
        if (z) values.push_back(*z);

        if (values.empty()) {
            return boost::none;
        }

        float sum = std::accumulate(values.begin(), values.end(), 0.0f);
        return sum / static_cast<float>(values.size());
    }
};