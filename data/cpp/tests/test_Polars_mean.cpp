
#include <gtest/gtest.h>
#include "../src/function_Polars_mean.cpp"

class TestPolarsExample : public ::testing::Test {
protected:
    PolarsExample calc;
};

TEST_F(TestPolarsExample, TestComputeMeanMultiplePositiveNumbers) {
    auto result = calc.compute_mean(1, 2, 3);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 2.0f);
}

TEST_F(TestPolarsExample, TestComputeMeanMultipleNegativeNumbers) {
    auto result = calc.compute_mean(-1, -2, -3);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, -2.0f);
}

TEST_F(TestPolarsExample, TestComputeMeanMixedNumbers) {
    auto result = calc.compute_mean(1, -1, 2);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 0.6666666666666666f);
}

TEST_F(TestPolarsExample, TestComputeMeanSingleNumber) {
    auto result = calc.compute_mean(10);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 10.0f);
}

TEST_F(TestPolarsExample, TestComputeMeanNoInput) {
    auto result = calc.compute_mean(10, boost::none, boost::none);
    ASSERT_TRUE(result);
    EXPECT_FLOAT_EQ(*result, 10.0f);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}