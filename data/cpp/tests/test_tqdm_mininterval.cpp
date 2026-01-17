#include <gtest/gtest.h>
#include "../src/function_tqdm_mininterval.cpp"

class TestMinintervalFunction : public ::testing::Test {
protected:
    void SetUp() override {}
    void TearDown() override {}
};

TEST_F(TestMinintervalFunction, TestMinintervalVerySmall) {
    auto start_time = std::chrono::steady_clock::now();
    int result = ProgressBar::might_fail_function(0.01f);
    auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(
        std::chrono::steady_clock::now() - start_time).count() / 1000.0;
    
    EXPECT_LT(elapsed_time, 0.1 * 10);
    EXPECT_EQ(result, 10);
}

TEST_F(TestMinintervalFunction, TestMinintervalVeryLarge) {
    auto start_time = std::chrono::steady_clock::now();
    int result = ProgressBar::might_fail_function(2.0f);
    auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(
        std::chrono::steady_clock::now() - start_time).count() / 1000.0;
    
    EXPECT_LT(elapsed_time, 2.0 * 10);
    EXPECT_EQ(result, 10);
}

TEST_F(TestMinintervalFunction, TestMinintervalZero) {
    auto start_time = std::chrono::steady_clock::now();
    int result = ProgressBar::might_fail_function(0.0f);
    auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(
        std::chrono::steady_clock::now() - start_time).count() / 1000.0;
    
    EXPECT_GT(elapsed_time, 0);
    EXPECT_LT(elapsed_time, 2.0);
    EXPECT_EQ(result, 10);
}

TEST_F(TestMinintervalFunction, TestMinintervalNonNumeric) {
    // 定义类型检查辅助函数
    auto check_input_type = [](const auto& value) {
        if (!std::is_same_v<std::decay_t<decltype(value)>, float>) {
            throw std::invalid_argument("mininterval must be a number");
        }
    };

    std::string non_numeric = "abc";
    try {
        check_input_type(non_numeric);
        FAIL() << "Expected TypeError for non-numeric mininterval";
    }
    catch(const std::invalid_argument& e) {
        EXPECT_EQ(std::string(e.what()), "mininterval must be a number");
    }
    catch(...) {
        FAIL() << "Expected TypeError for non-numeric mininterval";
    }
}

TEST_F(TestMinintervalFunction, TestMinintervalModifyDuringLoop) {
    int result1 = ProgressBar::might_fail_function(0.3f);
    EXPECT_EQ(result1, 10);

    int result2 = ProgressBar::might_fail_function(0.1f);
    EXPECT_EQ(result2, 10);
}