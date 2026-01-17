#include <gtest/gtest.h>
#include "../src/function_lightgbm_train.cpp"

class TestXGBoostTrainer : public ::testing::Test {
protected:
    XGBoostTrainer trainer;
};

TEST_F(TestXGBoostTrainer, TestTrainParams) {
    std::string boosting_type = "gbtree";
    std::string objective = "binary:logistic";
    std::string metric = "logloss";
    int num_leaves = 31;
    float learning_rate = 0.05f;
    float feature_fraction = 0.9f;
    float bagging_fraction = 0.8f;
    int bagging_freq = 5;
    int verbose = 0;
    int num_rounds = 1;
    int data_size = 100;
    int feature_size = 5;
    
    float model = trainer.train(
        boosting_type, objective, metric, num_leaves,
        learning_rate, feature_fraction, bagging_fraction,
        bagging_freq, verbose, num_rounds, data_size, feature_size
    );
    
    EXPECT_GE(model, 0.0f);
    EXPECT_LE(model, 1.0f);
}

TEST_F(TestXGBoostTrainer, TestModelTraining) {
    std::string boosting_type = "gbtree";
    std::string objective = "binary:logistic";
    std::string metric = "logloss";
    int num_leaves = 31;
    float learning_rate = 0.05f;
    float feature_fraction = 0.9f;
    float bagging_fraction = 0.8f;
    int bagging_freq = 5;
    int verbose = 0;
    int num_rounds = 1;
    int data_size = 100;
    int feature_size = 5;
    
    float model = trainer.train(
        boosting_type, objective, metric, num_leaves,
        learning_rate, feature_fraction, bagging_fraction,
        bagging_freq, verbose, num_rounds, data_size, feature_size
    );
    
    EXPECT_GE(model, 0.0f);
    EXPECT_LE(model, 1.0f);
}

TEST_F(TestXGBoostTrainer, TestPredictionLength) {
    std::string boosting_type = "gbtree";
    std::string objective = "binary:logistic";
    std::string metric = "logloss";
    int num_leaves = 31;
    float learning_rate = 0.05f;
    float feature_fraction = 0.9f;
    float bagging_fraction = 0.8f;
    int bagging_freq = 5;
    int verbose = 0;
    int num_rounds = 1;
    int data_size = 100;
    int feature_size = 5;
    
    float predictions = trainer.train(
        boosting_type, objective, metric, num_leaves,
        learning_rate, feature_fraction, bagging_fraction,
        bagging_freq, verbose, num_rounds, data_size, feature_size
    );
    
    EXPECT_GE(predictions, 0.0f);
    EXPECT_LE(predictions, 1.0f);
}

TEST_F(TestXGBoostTrainer, TestMissingParamError) {
    std::string boosting_type = "gbtree";
    std::string objective = "";  // 空objective
    std::string metric = "logloss";
    int num_leaves = 31;
    float learning_rate = 0.05f;
    float feature_fraction = 0.9f;
    float bagging_fraction = 0.8f;
    int bagging_freq = 5;
    int verbose = 0;
    int num_rounds = 1;
    int data_size = 100;
    int feature_size = 5;
    
    EXPECT_THROW(
        trainer.train(
            boosting_type, objective, metric, num_leaves,
            learning_rate, feature_fraction, bagging_fraction,
            bagging_freq, verbose, num_rounds, data_size, feature_size
        ),
        std::invalid_argument
    );
}

TEST_F(TestXGBoostTrainer, TestInvalidRoundTypeError) {

    std::string boosting_type = "gbtree";
    std::string objective = "binary:logistic";
    std::string metric = "logloss";
    int num_leaves = 31;
    float learning_rate = 0.05f;
    float feature_fraction = 0.9f;
    float bagging_fraction = 0.8f;
    int bagging_freq = 5;
    int verbose = 0;
    int num_rounds = -1;  // 无效值
    int data_size = 100;
    int feature_size = 5;
    
  
    EXPECT_THROW(
        trainer.train(
            boosting_type, objective, metric, num_leaves,
            learning_rate, feature_fraction, bagging_fraction,
            bagging_freq, verbose, num_rounds, data_size, feature_size
        ),
        std::invalid_argument
    );
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}