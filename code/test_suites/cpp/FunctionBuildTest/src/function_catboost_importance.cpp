#include <stdexcept>
#include <string>
#include <vector>
#include <numeric>

class CatBoostFeatureImportance {
private:
    bool modelInitialized = false;
    bool trainPoolInitialized = false;
    double importanceValue = 0.0;

public:
    float process(const std::string& action, float feature1, float feature2, float feature3, int label, const std::string& importance_type) {
        if (!modelInitialized) {
            modelInitialized = true;
            trainPoolInitialized = false;
        }

        if (action == "train") {
            if (label == 0) {
                throw std::invalid_argument("Label is required for training.");
            }
            importanceValue = feature1 + feature2 + feature3 + label;
            trainPoolInitialized = true;
            return 0.0f;
        } else if (action == "importance") {
            if (!(importance_type == "PredictionValuesChange" || importance_type == "LossFunctionChange" || importance_type == "ShapValues")) {
                throw std::invalid_argument("Invalid importance type. Choose from 'PredictionValuesChange', 'LossFunctionChange', or 'ShapValues'.");
            }
            double result;
            if (importance_type == "LossFunctionChange" || importance_type == "ShapValues") {
                if (!trainPoolInitialized) {
                    throw std::invalid_argument("Training dataset is required for this importance type.");
                }
                if (importance_type == "LossFunctionChange") {
                    result = importanceValue + 1.0;
                } else { // "ShapValues"
                    std::vector<double> importances = { importanceValue + 2.0, importanceValue + 2.5, importanceValue + 3.0 };
                    result = std::accumulate(importances.begin(), importances.end(), 0.0) / importances.size();
                }
            } else { // "PredictionValuesChange"
                result = importanceValue;
            }
            return static_cast<float>(result);
        } else {
            throw std::invalid_argument("Invalid action. Use 'train' or 'importance'.");
        }
    }
};