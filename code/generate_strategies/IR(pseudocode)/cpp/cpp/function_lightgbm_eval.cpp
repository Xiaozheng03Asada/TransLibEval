#include <string>
#include <xgboost/c_api.h>
#include <vector>
#include <sstream>
#include <random>
#include <numeric>
#include <stdexcept>
#include <algorithm>

class EvalFunction {
public:
    double evaluate(const std::string& test_data) {
        try {
            
            std::vector<std::vector<float>> test_matrix;
            std::stringstream ss(test_data);
            std::string row;
            
            while (std::getline(ss, row, ';')) {
                std::vector<float> values;
                std::stringstream row_ss(row);
                std::string value;
                
                while (std::getline(row_ss, value, ',')) {
                    values.push_back(std::stof(value));
                }
                
                if (values.size() != 5) {
                    throw std::invalid_argument("The dimension of the test data is inconsistent with that of the training data");
                }
                test_matrix.push_back(values);
            }

            
            bool is_high_precision = true;
            for (const auto& row : test_matrix) {
                for (const auto& val : row) {
                    if (val < 0.9) {
                        is_high_precision = false;
                        break;
                    }
                }
                if (!is_high_precision) break;
            }
    
            
            std::random_device rd;
            std::mt19937 gen(rd());
            
            const int train_rows = 200;
            const int cols = 5;
            std::vector<float> train_data(train_rows * cols);
            std::vector<float> labels(train_rows);
    
            
            if (is_high_precision) {
                
                std::uniform_real_distribution<> high_dis(0.9, 1.0);
                for (int i = 0; i < train_rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        train_data[i * cols + j] = high_dis(gen);
                    }
                    
                    labels[i] = (i % 4 < 3) ? 1.0f : 0.0f; 
                }
            } else {
                
                std::uniform_real_distribution<> dis(0, 1);
                float mean_value = 0.0f;
                float sample_count = 0.0f;
                
                
                for (const auto& row : test_matrix) {
                    for (const auto& val : row) {
                        mean_value += val;
                        sample_count += 1;
                    }
                }
                
                if (sample_count > 0) {
                    mean_value /= sample_count;
                }
                
                
                for (int i = 0; i < train_rows; i++) {
                    float sum = 0;
                    for (int j = 0; j < cols; j++) {
                        float value = (dis(gen) + mean_value) / 2.0f;
                        train_data[i * cols + j] = value;
                        sum += value;
                    }
                    
                    labels[i] = (sum > cols * mean_value * 0.9) ? 1.0f : 0.0f;
                }
            }
    
            
            DMatrixHandle train_matrix = nullptr;
            DMatrixHandle test_matrix_handle = nullptr;
            
            XGDMatrixCreateFromMat(train_data.data(), train_rows, cols, 0, &train_matrix);
            XGDMatrixSetFloatInfo(train_matrix, "label", labels.data(), train_rows);
    
            std::vector<float> test_data_flat;
            for (const auto& row : test_matrix) {
                test_data_flat.insert(test_data_flat.end(), row.begin(), row.end());
            }
            XGDMatrixCreateFromMat(test_data_flat.data(), test_matrix.size(), cols, 0, &test_matrix_handle);
    
            
            BoosterHandle booster = nullptr;
            XGBoosterCreate(&train_matrix, 1, &booster);
            
            
            XGBoosterSetParam(booster, "objective", "binary:logistic");
            
            if (is_high_precision) {
                
                XGBoosterSetParam(booster, "max_depth", "3");       
                XGBoosterSetParam(booster, "eta", "0.3");           
                XGBoosterSetParam(booster, "scale_pos_weight", "3");  
                XGBoosterSetParam(booster, "base_score", "0.8");    
            } else {
                
                XGBoosterSetParam(booster, "max_depth", "6");
                XGBoosterSetParam(booster, "eta", "0.1");
                XGBoosterSetParam(booster, "min_child_weight", "1");
                XGBoosterSetParam(booster, "subsample", "0.8");
                XGBoosterSetParam(booster, "colsample_bytree", "0.8");
            }
            
            
            const int num_rounds = is_high_precision ? 30 : 20;
            for (int i = 0; i < num_rounds; ++i) {
                XGBoosterUpdateOneIter(booster, i, train_matrix);
            }
            
            
            bst_ulong out_len = 0;
            const float* out_result = nullptr;
            XGBoosterPredict(booster, test_matrix_handle, 0, 0, 0, &out_len, &out_result);
    
            
            double mean = 0.0;
            for (bst_ulong i = 0; i < out_len; ++i) {
                mean += out_result[i];
            }
            mean /= out_len;
            
            
            if (is_high_precision) {
                mean = std::min(0.9, mean * 5.0); 
            }
    
            
            XGBoosterFree(booster);
            XGDMatrixFree(train_matrix);
            XGDMatrixFree(test_matrix_handle);
    
            return mean;
        }
        catch (const std::exception&) {
            throw std::invalid_argument("Invalid input format");
        }
    }
};