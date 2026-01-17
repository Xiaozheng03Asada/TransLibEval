#include <gtest/gtest.h>
#include "../src/function_tqdm_trange.cpp"

class TestRangeFunction : public ::testing::Test {
protected:
    void SetUp() override {}
    void TearDown() override {}
};

TEST_F(TestRangeFunction, TestTypeOfResult) {
    std::string result = ProgressRange::might_fail_function(0, 5, 1);
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
}

TEST_F(TestRangeFunction, TestCustomMiniters) {
    std::string result = ProgressRange::might_fail_function(0, 5, 1, "Test", true, 2);
    EXPECT_EQ(result.length(), 5);
}

TEST_F(TestRangeFunction, TestCustomMininterval) {
    std::string result = ProgressRange::might_fail_function(0, 5, 1, "Test", true, 0, 0.0f, 0.1f);
    EXPECT_EQ(result.length(), 5);
}

TEST_F(TestRangeFunction, TestDescUpdated) {
    std::string result = ProgressRange::might_fail_function(0, 5, 1, "New description");
    EXPECT_EQ(result.length(), 5);
}

TEST_F(TestRangeFunction, TestAsciiFalse) {
    std::string result = ProgressRange::might_fail_function(0, 5, 1, "Test", false);
    EXPECT_EQ(result.length(), 5);
}