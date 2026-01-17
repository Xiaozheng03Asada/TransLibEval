
#include <string>
#include <sstream>
#include <vector>
#include <xgboost/c_api.h>
#include <random>
#include <stdexcept>
class XGBoostPredictor {
public:
    std::string quick_sort_from_string(const std::string& input_str){
        try {
            // 解析输入字符串为测试数据矩阵
            std::vector<std::vector<float>> test_matrix;
            std::stringstream ss(input_str);
            std::string row;
            
            while (std::getline(ss, row, ';')) {
                std::vector<float> values;
                std::stringstream row_ss(row);
                std::string value;
                
                while (std::getline(row_ss, value, ',')) {
                    values.push_back(std::stof(value));
                }
                test_matrix.push_back(values);
            }
            
            // 验证所有行的长度相同
            size_t expected_cols = test_matrix.empty() ? 0 : test_matrix[0].size();
            for (const auto& row : test_matrix) {
                if (row.size() != expected_cols) {
                    throw std::invalid_argument("The dimension of the test data is inconsistent");
                }
            }
            
            // 生成随机训练数据
            std::random_device rd;
            std::mt19937 gen(rd());
            std::uniform_real_distribution<> dis(0, 1);
            
            const int train_rows = 100;
            const int cols = 5; // 与Python代码一致
            
            if (expected_cols != cols) {
                throw std::invalid_argument("The dimension of the test data is inconsistent with that of the training data");
            }
            
            std::vector<float> X(train_rows * cols);
            std::vector<float> y(train_rows);
            
            for (int i = 0; i < train_rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    X[i * cols + j] = dis(gen);
                }
                y[i] = std::bernoulli_distribution(0.5)(gen) ? 1.0f : 0.0f;
            }
            
            // 创建XGBoost的DMatrix
            DMatrixHandle train_matrix = nullptr;
            XGDMatrixCreateFromMat(X.data(), train_rows, cols, 0, &train_matrix);
            XGDMatrixSetFloatInfo(train_matrix, "label", y.data(), train_rows);
            
            // 平铺测试数据
            std::vector<float> test_flat;
            for (const auto& row : test_matrix) {
                test_flat.insert(test_flat.end(), row.begin(), row.end());
            }
            
            DMatrixHandle test_matrix_handle = nullptr;
            XGDMatrixCreateFromMat(test_flat.data(), test_matrix.size(), cols, 0, &test_matrix_handle);
            
            // 训练模型
            BoosterHandle booster = nullptr;
            XGBoosterCreate(&train_matrix, 1, &booster);
            XGBoosterSetParam(booster, "objective", "binary:logistic");
            
            for (int i = 0; i < 5; ++i) { // 5轮训练，与Python代码一致
                XGBoosterUpdateOneIter(booster, i, train_matrix);
            }
            
            // 预测
            bst_ulong out_len = 0;
            const float* out_result = nullptr;
            XGBoosterPredict(booster, test_matrix_handle, 0, 0, 0, &out_len, &out_result);
            
            // 将结果转换为逗号分隔的字符串
            std::stringstream result_ss;
            for (bst_ulong i = 0; i < out_len; ++i) {
                if (i > 0) {
                    result_ss << ",";
                }
                result_ss << out_result[i];
            }
            
            // 清理
            XGBoosterFree(booster);
            XGDMatrixFree(train_matrix);
            XGDMatrixFree(test_matrix_handle);
            
            return result_ss.str();
        } catch (const std::exception& e) {
            throw std::invalid_argument("Invalid input format: " + std::string(e.what()));
        }
    }
}; 