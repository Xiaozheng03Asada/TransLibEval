#include <boost/numeric/ublas/matrix.hpp>
#include <boost/numeric/ublas/vector.hpp>
#include <boost/math/statistics/linear_regression.hpp>
#include <string>
#include <vector>
#include <stdexcept>
#include <cmath>

class BoostFeatureImportance
{
public:

    float process(const std::string &action, float feature1, float feature2, float feature3,
                  int label = -1, const std::string &importance_type = "")
    {
        namespace ublas = boost::numeric::ublas;

        if (action == "train")
        {
            if (label == -1)
            {
                throw std::invalid_argument("Label is required for training.");
            }

            // 准备训练数据
            features = ublas::matrix<double>(3, 3);
            features(0, 0) = feature1;
            features(0, 1) = feature2;
            features(0, 2) = feature3;
            features(1, 0) = feature1 + 0.1;
            features(1, 1) = feature2 - 0.2;
            features(1, 2) = feature3 + 0.3;
            features(2, 0) = feature1 - 0.2;
            features(2, 1) = feature2 + 0.1;
            features(2, 2) = feature3 - 0.1;

            labels = ublas::vector<double>(3);
            labels(0) = label;
            labels(1) = 1 - label;
            labels(2) = label;

            trained = true;
            return 0.0f;
        }
        else if (action == "importance")
        {
            if (!trained)
            {
                throw std::invalid_argument("Model must be trained first.");
            }

            if (importance_type != "PredictionValuesChange" &&
                importance_type != "LossFunctionChange" &&
                importance_type != "ShapValues")
            {
                throw std::invalid_argument("Invalid importance type.");
            }

            ublas::vector<double> importances(3);

            if (importance_type == "PredictionValuesChange")
            {
                // 改进的特征重要性计算
                for (size_t i = 0; i < 3; ++i)
                {
                    ublas::vector<double> feature_col(3);
                    for (size_t j = 0; j < 3; ++j)
                    {
                        feature_col(j) = features(j, i);
                    }

                    // 标准化特征
                    double mean_x = 0, std_x = 0;
                    for (size_t k = 0; k < 3; ++k)
                    {
                        mean_x += feature_col(k);
                    }
                    mean_x /= 3;

                    for (size_t k = 0; k < 3; ++k)
                    {
                        std_x += std::pow(feature_col(k) - mean_x, 2);
                    }
                    std_x = std::sqrt(std_x / 3);

                    // 标准化后的特征值
                    for (size_t k = 0; k < 3; ++k)
                    {
                        feature_col(k) = (feature_col(k) - mean_x) / (std_x + 1e-10);
                    }

                    // 计算加权相关系数
                    double weighted_correlation = 0;
                    for (size_t k = 0; k < 3; ++k)
                    {
                        weighted_correlation += std::abs(feature_col(k) * labels(k));
                    }

                    // 添加非线性变换
                    importances(i) = std::tanh(weighted_correlation / 3.0);
                }
            }
            else if (importance_type == "LossFunctionChange")
            {
                // 使用均方误差变化作为特征重要性
                for (size_t i = 0; i < 3; ++i)
                {
                    ublas::vector<double> feature_col(3);
                    for (size_t j = 0; j < 3; ++j)
                    {
                        feature_col(j) = features(j, i);
                    }
                    double mse = 0.0;
                    for (size_t j = 0; j < 3; ++j)
                    {
                        mse += std::pow(feature_col(j) - labels(j), 2);
                    }
                    importances(i) = mse / 3.0;
                }
            }
            else
            { // ShapValues
                // 使用简化的SHAP值计算
                for (size_t i = 0; i < 3; ++i)
                {
                    importances(i) = std::abs(features(0, i) -
                                              (features(1, i) + features(2, i)) / 2.0);
                }
            }

            return static_cast<float>(importances(0));
        }
        else
        {
            throw std::invalid_argument("Invalid action. Use 'train' or 'importance'.");
        }
    }

private:
    boost::numeric::ublas::matrix<double> features;
    boost::numeric::ublas::vector<double> labels;
    bool trained;
};