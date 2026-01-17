#include <gtest/gtest.h>
#include "../src/function_tenacity_stop_after_attempt.cpp"

class TestMightFailFunction : public ::testing::Test {
protected:
    RetryFunction func;
};

TEST_F(TestMightFailFunction, TestSuccessOnFirstTry) {
    int result = func.might_fail_function(6);
    EXPECT_EQ(result, 6);
}

TEST_F(TestMightFailFunction, TestSuccessAfterRetry) {
    int result = func.might_fail_function(5);
    EXPECT_EQ(result, 5);
}

TEST_F(TestMightFailFunction, TestNoRetryOnTooSmallValue) {
    EXPECT_THROW({
        func.might_fail_function(1, 0, false);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestRuntimeErrorOnTooSmallValue) {
    EXPECT_THROW({
        func.might_fail_function(1, 0, true);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestRuntimeErrorWithExtraSleep) {
    EXPECT_THROW({
        func.might_fail_function(4, 0.5, true);
    }, std::runtime_error);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}