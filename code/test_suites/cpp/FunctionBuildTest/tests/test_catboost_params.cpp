#include <gtest/gtest.h>
#include "../src/function_catboost_params.cpp"

class TestBoostParamFetcher : public ::testing::Test {
protected:
    void SetUp() override {
        param_fetcher = new BoostParamFetcher();
    }

    void TearDown() override {
        delete param_fetcher;
    }

    BoostParamFetcher* param_fetcher;
};

TEST_F(TestBoostParamFetcher, TestReturnType) {
    std::string result = param_fetcher->get_model_params(150, 7, 0.1f);
    ASSERT_TRUE(result.length() > 0);
}

TEST_F(TestBoostParamFetcher, TestCheckIterations) {
    std::string result = param_fetcher->get_model_params(200, 6, 0.1f);
    ASSERT_TRUE(result.find("Iterations: 200") != std::string::npos);
}

TEST_F(TestBoostParamFetcher, TestCheckDepth) {
    std::string result = param_fetcher->get_model_params(100, 10, 0.05f);
    ASSERT_TRUE(result.find("Depth: 10") != std::string::npos);
}

TEST_F(TestBoostParamFetcher, TestCheckLearningRate) {
    std::string result = param_fetcher->get_model_params(100, 6, 0.01f);
    ASSERT_TRUE(result.find("Learning Rate: 0.01") != std::string::npos);
}

TEST_F(TestBoostParamFetcher, TestMultipleConditions) {
    std::string result = param_fetcher->get_model_params(250, 8, 0.15f);
    ASSERT_TRUE(result.find("Iterations: 250") != std::string::npos);
    ASSERT_TRUE(result.find("Depth: 8") != std::string::npos);
    ASSERT_TRUE(result.find("Learning Rate: 0.15") != std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}