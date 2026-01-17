
#include <gtest/gtest.h>
#include "../src/function_Polars_filter.cpp"

class TestPolarsExample : public ::testing::Test {
protected:
    PolarsExample calc;
};

TEST_F(TestPolarsExample, TestFilterNumbersGreaterThanThreshold) {
    auto result = calc.filter_numbers(2, 1, 2, 3);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 3.0f);
}

TEST_F(TestPolarsExample, TestFilterNumbersMultipleValues) {
    auto result = calc.filter_numbers(1, 5, 2, 7);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 7.0f);
}

TEST_F(TestPolarsExample, TestFilterNumbersEmptyResult) {
    auto result = calc.filter_numbers(10, 1, 2, 3);
    EXPECT_FALSE(result);
}

TEST_F(TestPolarsExample, TestFilterNumbersSingleValueGreaterThanThreshold) {
    auto result = calc.filter_numbers(0, 1);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 1.0f);
}

TEST_F(TestPolarsExample, TestFilterNumbersNoValuesAboveThreshold) {
    auto result = calc.filter_numbers(100, 10, 20, 30);
    EXPECT_FALSE(result);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}