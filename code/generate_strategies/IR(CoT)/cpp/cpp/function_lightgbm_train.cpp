
#include <string>
#include <xgboost/c_api.h>
#include <random>
#include <stdexcept>
class XGBoostTrainer
{
public:
    float train(
        const std::string &boosting_type,
        const std::string &objective,
        const std::string &metric,
        int num_leaves,
        float learning_rate,
        float feature_fraction,
        float bagging_fraction,
        int bagging_freq,
        int verbose,
        int num_rounds,
        int data_size,
        int feature_size)
    {

        if (num_rounds <= 0)
        {
            throw std::invalid_argument("num_rounds must be a positive integer");
        }
        
        if (objective.empty())
        {
            throw std::invalid_argument("train_params must contain the 'objective' key");
        }

        
        std::mt19937 gen(42); 
        std::uniform_real_distribution<float> dis(0.0, 1.0);
        std::bernoulli_distribution bdis(0.5);

        std::vector<float> X(data_size * feature_size);
        std::vector<float> y(data_size);

        for (int i = 0; i < data_size; i++)
        {
            for (int j = 0; j < feature_size; j++)
            {
                X[i * feature_size + j] = dis(gen);
            }
            y[i] = bdis(gen) ? 1.0f : 0.0f;
        }

        
        DMatrixHandle dtrain;
        XGDMatrixCreateFromMat(X.data(), data_size, feature_size, 0.0f, &dtrain);
        XGDMatrixSetFloatInfo(dtrain, "label", y.data(), data_size);

        
        BoosterHandle booster;
        XGBoosterCreate(&dtrain, 1, &booster);

        
        XGBoosterSetParam(booster, "booster", boosting_type == "gbdt" ? "gbtree" : boosting_type.c_str());
        XGBoosterSetParam(booster, "objective", objective.c_str());
        XGBoosterSetParam(booster, "eval_metric", metric.c_str());
        XGBoosterSetParam(booster, "max_leaves", std::to_string(num_leaves).c_str());
        XGBoosterSetParam(booster, "eta", std::to_string(learning_rate).c_str());
        XGBoosterSetParam(booster, "colsample_bytree", std::to_string(feature_fraction).c_str());
        XGBoosterSetParam(booster, "subsample", std::to_string(bagging_fraction).c_str());
        XGBoosterSetParam(booster, "subsample_freq", std::to_string(bagging_freq).c_str());
        XGBoosterSetParam(booster, "silent", std::to_string(verbose == 0).c_str());

        
        for (int i = 0; i < num_rounds; i++)
        {
            XGBoosterUpdateOneIter(booster, i, dtrain);
        }

        
        std::vector<float> new_data(10 * feature_size);
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < feature_size; j++)
            {
                new_data[i * feature_size + j] = dis(gen);
            }
        }

        
        DMatrixHandle dpredict;
        XGDMatrixCreateFromMat(new_data.data(), 10, feature_size, 0.0f, &dpredict);

        
        bst_ulong out_len = 0;
        const float *out_result = nullptr;
        XGBoosterPredict(booster, dpredict, 0, 0, 0, &out_len, &out_result);

        
        float prediction = out_result[0];

        
        XGBoosterFree(booster);
        XGDMatrixFree(dtrain);
        XGDMatrixFree(dpredict);

        return prediction;
    }
};