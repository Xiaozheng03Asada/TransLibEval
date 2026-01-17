#include <boost/property_tree/ptree.hpp>
#include <boost/lexical_cast.hpp>
#include <string>
#include <sstream>

class BoostParamFetcher {
public:
    std::string get_model_params(int iterations, int depth, float learning_rate) {
        boost::property_tree::ptree model_params;
        model_params.put("iterations", iterations);
        model_params.put("depth", depth);
        model_params.put("learning_rate", learning_rate);

        std::stringstream result;
        result << "Model Params - "
               << "Iterations: " << model_params.get<int>("iterations") << ", "
               << "Depth: " << model_params.get<int>("depth") << ", "
               << "Learning Rate: " << model_params.get<float>("learning_rate");

        return result.str();
    }
};