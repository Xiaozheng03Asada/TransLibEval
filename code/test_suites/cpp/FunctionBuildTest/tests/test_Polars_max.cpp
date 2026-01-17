#include <gtest/gtest.h>
#include "../src/function_Polars_max.cpp"

class TestPolarsExample : public ::testing::Test {
protected:
    PolarsExample calc;
};

TEST_F(TestPolarsExample, TestComputeMaxMultiplePositiveNumbers) {
    float result = calc.compute_max(1, 2, 3);
    EXPECT_FLOAT_EQ(result, 3.0f);
}

TEST_F(TestPolarsExample, TestComputeMaxMultipleNegativeNumbers) {
    float result = calc.compute_max(-1, -2, -3);
    EXPECT_FLOAT_EQ(result, -1.0f);
}

TEST_F(TestPolarsExample, TestComputeMaxMixedNumbers) {
    float result = calc.compute_max(1, -1, 2);
    EXPECT_FLOAT_EQ(result, 2.0f);
}

TEST_F(TestPolarsExample, TestComputeMaxSingleNumber) {
    float result = calc.compute_max(10);
    EXPECT_FLOAT_EQ(result, 10.0f);
}

TEST_F(TestPolarsExample, TestComputeMaxNoInput) {
    float result = calc.compute_max(10, boost::none, boost::none);
    EXPECT_FLOAT_EQ(result, 10.0f);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}