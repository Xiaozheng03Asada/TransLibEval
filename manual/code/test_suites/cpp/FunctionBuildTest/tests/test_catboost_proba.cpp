#include <gtest/gtest.h>
#include "../src/function_catboost_proba.cpp"

class TestBoostProbabilityPredictor : public ::testing::Test {
protected:
    void SetUp() override {
        predictor = new BoostProbabilityPredictor();
    }

    void TearDown() override {
        delete predictor;
    }

    BoostProbabilityPredictor* predictor;
};

TEST_F(TestBoostProbabilityPredictor, TestBasicProbability) {
    float result = predictor->predict_probability(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 0, 0, 1, 1, 1, 3.5f);
    ASSERT_GE(result, 0.5f);
}

TEST_F(TestBoostProbabilityPredictor, TestHighProbability) {
    float result = predictor->predict_probability(10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 0, 0, 1, 1, 1, 35.0f);
    ASSERT_GE(result, 0.5f);
}

TEST_F(TestBoostProbabilityPredictor, TestLowProbability) {
    float result = predictor->predict_probability(10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 0, 0, 1, 1, 1, 5.0f);
    ASSERT_LT(result, 0.5f);
}

TEST_F(TestBoostProbabilityPredictor, TestEdgeCase) {
    float result = predictor->predict_probability(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 0, 0, 1, 1, 1, 10.0f);
    ASSERT_GE(result, 0.0f);
    ASSERT_LE(result, 1.0f);
}

TEST_F(TestBoostProbabilityPredictor, TestInvalidInputs) {
    ASSERT_THROW(
        predictor->predict_probability(1.0f, 2.0f, std::numeric_limits<float>::infinity(), 4.0f, 5.0f, 0, 0, 1, 1, 1, 3.5f),
        std::invalid_argument
    );
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}