#include <gtest/gtest.h>
#include "../src/function_catboost_importance.cpp"

class TestBoostFeatureImportance : public ::testing::Test {
protected:
    void SetUp() override {
        importance_calculator = new BoostFeatureImportance();
    }

    void TearDown() override {
        delete importance_calculator;
    }

    BoostFeatureImportance* importance_calculator;
};

TEST_F(TestBoostFeatureImportance, TestPredictionValuesChange) {
    importance_calculator->process("train", 1.0f, 2.0f, 3.0f, 0);
    float result = importance_calculator->process("importance", 1.0f, 2.0f, 3.0f, -1, "PredictionValuesChange");
    ASSERT_GE(result, 0.0f);
}

TEST_F(TestBoostFeatureImportance, TestLossFunctionChange) {
    importance_calculator->process("train", 4.0f, 5.0f, 6.0f, 1);
    float result = importance_calculator->process("importance", 4.0f, 5.0f, 6.0f, -1, "LossFunctionChange");
    ASSERT_GE(result, 0.0f);
}

TEST_F(TestBoostFeatureImportance, TestShapValues) {
    importance_calculator->process("train", 7.0f, 8.0f, 9.0f, 0);
    float result = importance_calculator->process("importance", 7.0f, 8.0f, 9.0f, -1, "ShapValues");
    ASSERT_GE(result, 0.0f);
}

TEST_F(TestBoostFeatureImportance, TestInvalidImportanceType) {
    importance_calculator->process("train", 10.0f, 11.0f, 12.0f, 1);
    ASSERT_THROW(
        importance_calculator->process("importance", 10.0f, 11.0f, 12.0f, -1, "InvalidType"),
        std::invalid_argument
    );
}

TEST_F(TestBoostFeatureImportance, TestDifferentInputs) {
    importance_calculator->process("train", 1.5f, 2.5f, 3.5f, 0);
    float result_1 = importance_calculator->process("importance", 1.5f, 2.5f, 3.5f, -1, "PredictionValuesChange");
    importance_calculator->process("train", 4.5f, 5.5f, 6.5f, 1);
    float result_2 = importance_calculator->process("importance", 4.5f, 5.5f, 6.5f, -1, "PredictionValuesChange");
    ASSERT_NE(result_1, result_2);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}