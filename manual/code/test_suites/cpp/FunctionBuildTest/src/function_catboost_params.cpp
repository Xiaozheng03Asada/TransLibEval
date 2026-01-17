#include <string>
#include <sstream>
#include <iomanip>

class CatBoostParamFetcher {
public:
    std::string getModelParams(int iterations, int depth, float learning_rate) {
        std::ostringstream oss;
        oss << "Model Params - Iterations: " << iterations << ", Depth: " << depth << ", Learning Rate: " << std::fixed << std::setprecision(2) << learning_rate;
        return oss.str();
    }
};