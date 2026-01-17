#include <gtest/gtest.h>
#include "../src/function_catboost_predict.cpp"

class TestBoostPredictor : public ::testing::Test {
protected:
    void SetUp() override {
        predictor = new BoostPredictor();
    }

    void TearDown() override {
        delete predictor;
    }

    BoostPredictor* predictor;
};

TEST_F(TestBoostPredictor, TestBasicClassification) {
    int result = predictor->predict_class(1, 2, 3, 4, 5, 0, 0, 1, 1, 1, 3);
    ASSERT_EQ(result, 1);
}

TEST_F(TestBoostPredictor, TestCustomThreshold) {
    int result = predictor->predict_class(10, 15, 20, 25, 30, 0, 0, 1, 1, 1, 18);
    ASSERT_EQ(result, 1);
}

TEST_F(TestBoostPredictor, TestEdgeCaseLowValue) {
    int result = predictor->predict_class(5, 6, 7, 8, 9, 0, 0, 1, 1, 1, 4);
    ASSERT_EQ(result, 0);
}

TEST_F(TestBoostPredictor, TestEdgeCaseHighValue) {
    int result = predictor->predict_class(10, 20, 30, 40, 50, 0, 0, 1, 1, 1, 60);
    ASSERT_EQ(result, 1);
}

TEST_F(TestBoostPredictor, TestInvalidInputs) {
    ASSERT_THROW(
        predictor->predict_class(1, 2, 3, std::numeric_limits<float>::infinity(), 5, 0, 1, 1, 1, 1, 4),
        std::invalid_argument
    );
    ASSERT_THROW(
        predictor->predict_class(1, 2, 3, 4, 5, 0, 2, 1, 1, 1, 4),
        std::invalid_argument
    );
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}