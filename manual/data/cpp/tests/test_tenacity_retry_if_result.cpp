#include <gtest/gtest.h>
#include "../src/function_tenacity_retry_if_result.cpp"

class TestMightFailFunction : public ::testing::Test {
protected:
    RetryFunction func;
};

TEST_F(TestMightFailFunction, TestSuccessOnFirstTry) {
    int result = func.might_fail_function(6);
    EXPECT_EQ(result, 6);
}

TEST_F(TestMightFailFunction, TestFailureAfterMaxAttempts) {
    EXPECT_THROW({
        func.might_fail_function(2, true);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestRetryOnResult) {
    EXPECT_THROW({
        func.might_fail_function(2, true);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestNoRetryOnResult) {
    int result = func.might_fail_function(5);
    EXPECT_EQ(result, 5);
}

TEST_F(TestMightFailFunction, TestRetryWithCustomLogic) {
    EXPECT_THROW({
        func.might_fail_function(3, true);
    }, std::runtime_error);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}