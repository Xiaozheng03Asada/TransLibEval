#include <gtest/gtest.h>
#include "../src/function_tenacity_wait_random_exponential.cpp"


class TestMightFailFunction : public ::testing::Test {
protected:
    RetryFunction func;
};

TEST_F(TestMightFailFunction, TestSuccessAfterRetry) {
    int result = func.might_fail_function(5);
    EXPECT_EQ(result, 5);
}

TEST_F(TestMightFailFunction, TestZeroInput) {
    EXPECT_THROW({
        func.might_fail_function(0);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestRetryWithRandomExponentialBackoff) {
    auto start_time = std::chrono::high_resolution_clock::now();
    EXPECT_THROW({
        func.might_fail_function(2, 2);
    }, std::runtime_error);
    auto end_time = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> total_time = end_time - start_time;
    EXPECT_GT(total_time.count(), 6);
    EXPECT_LT(total_time.count(), 30);
}

TEST_F(TestMightFailFunction, TestSuccessOnFirstTry) {
    int result = func.might_fail_function(6);
    EXPECT_EQ(result, 6);
}

TEST_F(TestMightFailFunction, TestFailureAfterMaxAttempts) {
    EXPECT_THROW({
        func.might_fail_function(2);
    }, std::runtime_error);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}