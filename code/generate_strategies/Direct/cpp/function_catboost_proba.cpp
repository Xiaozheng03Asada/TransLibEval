#include <boost/numeric/ublas/matrix.hpp>
#include <boost/numeric/ublas/vector.hpp>
#include <boost/math/distributions/normal.hpp>
#include <vector>
#include <stdexcept>
#include <cmath>

class BoostProbabilityPredictor {
public:
    float predict_probability(float x1, float x2, float x3, float x4, float x5,
                            int y1, int y2, int y3, int y4, int y5, float value) {
        namespace ublas = boost::numeric::ublas;

        // 验证输入
        std::vector<float> features = {x1, x2, x3, x4, x5, value};
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

        // 创建训练数据
        ublas::matrix<double> train_data(5, 1);
        for (size_t i = 0; i < 5; ++i) {
            train_data(i, 0) = features[i];
        }

        // 计算基于距离的概率
        double mean = 0.0;
        double std_dev = 0.0;

        // 计算均值
        for (size_t i = 0; i < 5; ++i) {
            mean += train_data(i, 0);
        }
        mean /= 5.0;

        // 计算标准差
        for (size_t i = 0; i < 5; ++i) {
            std_dev += std::pow(train_data(i, 0) - mean, 2);
        }
        std_dev = std::sqrt(std_dev / 5.0);

        // 使用正态分布计算概率
        boost::math::normal_distribution<double> dist(mean, std_dev);
        double prob = boost::math::cdf(dist, value);

        // 确保返回值在 [0,1] 范围内
        return static_cast<float>(std::max(0.0, std::min(1.0, prob)));
    }
};