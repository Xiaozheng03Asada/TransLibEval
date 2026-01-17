#include <gtest/gtest.h>
#include "../src/function_lightgbm_eval.cpp"

class TestEvalFunction : public ::testing::Test {
protected:
    EvalFunction eval_function;
    const std::string test_data = "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;"
                                 "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;"
                                 "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5";
};

TEST_F(TestEvalFunction, ModelTrainingWithEvalMetric) {
    EXPECT_NO_THROW(eval_function.evaluate(test_data));
}

TEST_F(TestEvalFunction, EvalMetricLogloss) {
    double result = eval_function.evaluate(test_data);
    EXPECT_GT(result, 0.0);
}

TEST_F(TestEvalFunction, EvalMetricAUC) {
    std::string modified_test_data = "0.3,0.5,0.1,0.2,0.4;0.4,0.3,0.2,0.1,0.5;"
                                   "0.6,0.1,0.5,0.4,0.3;0.1,0.2,0.3,0.4,0.5";
    double result = eval_function.evaluate(modified_test_data);
    EXPECT_GT(result, 0.4);
}

TEST_F(TestEvalFunction, EvalWithHighPrecision) {
    std::string high_precision_data = "0.998,0.999,0.997,0.996,0.995;"
                                    "0.994,0.995,0.996,0.997,0.998;"
                                    "0.995,0.996,0.997,0.998,0.999";
    double result = eval_function.evaluate(high_precision_data);
    EXPECT_GT(result, 0.5);
}

TEST_F(TestEvalFunction, EvalWithInvalidData) {
    std::string invalid_test_data = "0.1,0.2,0.3,0.4;0.1,0.2,0.3";
    EXPECT_THROW(eval_function.evaluate(invalid_test_data), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}