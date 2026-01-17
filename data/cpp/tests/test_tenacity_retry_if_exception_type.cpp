#include <gtest/gtest.h>
#include "../src/function_tenacity_retry_if_exception_type.cpp"

class TestMightFailFunction : public ::testing::Test {
protected:
    RetryFunction func;
};

TEST_F(TestMightFailFunction, TestSuccessAfterRetry) {
    int result = func.might_fail_function(5);
    EXPECT_EQ(result, 5);
}

TEST_F(TestMightFailFunction, TestFailureAfterMaxAttempts) {
    EXPECT_THROW({
        func.might_fail_function(-1, true);
    }, std::runtime_error);
}

TEST_F(TestMightFailFunction, TestRetryOnValueErrorWithCustomMessage) {
    try {
        func.might_fail_function(2, true);
    } catch (const std::runtime_error& e) {
        EXPECT_TRUE(std::string(e.what()).find("Value is too small") != std::string::npos);
    }
}

TEST_F(TestMightFailFunction, TestNoRetryOnException) {
    EXPECT_THROW({
        func.might_fail_function(5, true);
        throw std::exception();
    }, std::exception);
}

TEST_F(TestMightFailFunction, TestRetryOnMultipleValueErrors) {
    EXPECT_THROW({
        for (int i = 0; i < 4; ++i) {
            func.might_fail_function(2, true);
        }
    }, std::runtime_error);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}