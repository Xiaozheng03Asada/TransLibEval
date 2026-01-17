#include <string>
#include <xgboost/c_api.h>
#include <vector>
#include <random>
#include <sstream>
#include <stdexcept>

class FeatureImportance {
public:
    std::string quick_sort_from_string(const std::string& input_str) {
        try {
           
            std::stringstream ss(input_str);
            std::string token;
            std::vector<int> params;
            while (std::getline(ss, token, ',')) {
                params.push_back(std::stoi(token));
            }
            
            if (params.size() != 2) {
                throw std::invalid_argument("Invalid input format");
            }
            
            int num_samples = params[0];
            int num_features = params[1];
    
            
            std::random_device rd;
            std::mt19937 gen(rd());
            std::uniform_real_distribution<> dis(0, 1);
    
            std::vector<float> X(num_samples * num_features);
            std::vector<float> y(num_samples);
    
            for (int i = 0; i < num_samples; ++i) {
                float sum = 0;
                for (int j = 0; j < num_features; ++j) {
                    float value = dis(gen);
                    X[i * num_features + j] = value;
                    sum += value;
                }
                y[i] = (sum > num_features / 2) ? 1.0f : 0.0f;
            }
    
          
            DMatrixHandle dtrain;
            XGDMatrixCreateFromMat(X.data(), num_samples, num_features, 0, &dtrain);
            XGDMatrixSetFloatInfo(dtrain, "label", y.data(), num_samples);
    
           
            BoosterHandle booster;
            XGBoosterCreate(&dtrain, 1, &booster);
            XGBoosterSetParam(booster, "objective", "binary:logistic");
            XGBoosterSetParam(booster, "eval_metric", "logloss");
            XGBoosterSetParam(booster, "max_depth", "5");
            XGBoosterSetParam(booster, "eta", "0.05");
            XGBoosterSetParam(booster, "subsample", "0.9");
    
            
            for (int i = 0; i < 5; ++i) {
                XGBoosterUpdateOneIter(booster, i, dtrain);
            }
    
          
            std::vector<std::string> feat_names(num_features);
            std::vector<const char*> feat_names_char(num_features);
            for (int i = 0; i < num_features; ++i) {
                feat_names[i] = "f" + std::to_string(i);
                feat_names_char[i] = feat_names[i].c_str();
            }
            
          
            XGBoosterSetStrFeatureInfo(booster, "feature_name", 
                                      feat_names_char.data(), num_features);
                                      
            
            bst_ulong out_len;
            const char** dump_array;
            XGBoosterDumpModelEx(booster, "", 0, "json", &out_len, &dump_array);
           
            std::vector<int> importance(num_features, 0);
            
          
            for (bst_ulong i = 0; i < out_len; ++i) {
                std::string tree_str(dump_array[i]);
               
                for (int j = 0; j < num_features; ++j) {
                    std::string feature_id = "f" + std::to_string(j);
                    size_t pos = 0;
                    while ((pos = tree_str.find(feature_id, pos)) != std::string::npos) {
                        importance[j]++;
                        pos += feature_id.length();
                    }
                }
            }
    
           
            std::stringstream result;
            for (int i = 0; i < num_features; ++i) {
                if (i > 0) result << ",";
                result << importance[i];
            }
    
          
            XGBoosterFree(booster);
            XGDMatrixFree(dtrain);
    
            return result.str();
        }
        catch (const std::exception&) {
            throw std::invalid_argument("Invalid input format");
        }
    }
};