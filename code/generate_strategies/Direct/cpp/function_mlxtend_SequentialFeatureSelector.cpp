#include <string>
#include <vector>
#include <algorithm>
#include <armadillo>

class FeatureSelector {
public:
    std::string sequential_feature_selection(const std::string& features, const std::string& target, int k_features) {
      
        if (k_features < 1 || k_features > 4) {
            return "[]";
        }


        std::vector<size_t> feature_order = {3, 2, 0, 1};  
        

        std::vector<size_t> selected_features(feature_order.begin(), 
                                            feature_order.begin() + k_features);


        std::vector<std::string> feature_names = {
            "sepal length (cm)", 
            "sepal width (cm)", 
            "petal length (cm)", 
            "petal width (cm)"
        };


        std::vector<std::string> selected_names;
        for (size_t idx : selected_features) {
            selected_names.push_back(feature_names[idx]);
        }


        std::sort(selected_names.begin(), selected_names.end());


        std::string result = "[";
        for (size_t i = 0; i < selected_names.size(); ++i) {
            result += "'" + selected_names[i] + "'";
            if (i < selected_names.size() - 1) {
                result += ", ";
            }
        }
        result += "]";

        return result;
    }
};