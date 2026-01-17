#include <boost/numeric/ublas/matrix.hpp>
#include <boost/numeric/ublas/vector.hpp>
#include <vector>
#include <stdexcept>
#include <algorithm>
#include <numeric>

class BoostPredictor {
public:
    int predict_class(float x1, float x2, float x3, float x4, float x5,
                     int y1, int y2, int y3, int y4, int y5, float value) {
        
        std::vector<float> features = {x1, x2, x3, x4, x5};
        std::vector<int> labels = {y1, y2, y3, y4, y5};

        for (const auto& x : features) {
            if (!std::isfinite(x)) {
                throw std::invalid_argument("Feature values and input must be integers or floats.");
            }
        }

        for (const auto& y : labels) {
            if (y != 0 && y != 1) {
                throw std::invalid_argument("Labels must be integers.");
            }
        }

        
        float max_negative = -std::numeric_limits<float>::max();
        float min_positive = std::numeric_limits<float>::max();
        bool has_positive = false;
        bool has_negative = false;

        for (size_t i = 0; i < features.size(); ++i) {
            if (labels[i] == 1) {
                has_positive = true;
                min_positive = std::min(min_positive, features[i]);
            } else {
                has_negative = true;
                max_negative = std::max(max_negative, features[i]);
            }
        }

        
        float threshold;
        if (has_positive && has_negative) {
            threshold = (max_negative + min_positive) / 2.0f;
        } else if (has_positive) {
            threshold = min_positive;
        } else {
            threshold = std::accumulate(features.begin(), features.end(), 0.0f) / features.size();
        }

        
        return value >= threshold ? 1 : 0;
    }
};