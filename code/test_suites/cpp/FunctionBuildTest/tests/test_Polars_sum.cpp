#include <gtest/gtest.h>
#include "../src/function_Polars_sum.cpp"

class TestPolarsExample : public ::testing::Test {
protected:
    PolarsExample calc;
};

TEST_F(TestPolarsExample, TestComputeSumMultiplePositiveNumbers) {
    float result = calc.compute_sum(1, 2, 3);
    EXPECT_FLOAT_EQ(result, 6.0f);
}

TEST_F(TestPolarsExample, TestComputeSumMultipleNegativeNumbers) {
    float result = calc.compute_sum(-1, -2, -3);
    EXPECT_FLOAT_EQ(result, -6.0f);
}

TEST_F(TestPolarsExample, TestComputeSumMixedNumbers) {
    float result = calc.compute_sum(1, -1, 2);
    EXPECT_FLOAT_EQ(result, 2.0f);
}

TEST_F(TestPolarsExample, TestComputeSumSingleNumber) {
    float result = calc.compute_sum(10);
    EXPECT_FLOAT_EQ(result, 10.0f);
}

TEST_F(TestPolarsExample, TestComputeSumNoInput) {
    float result = calc.compute_sum(10, boost::none, boost::none);
    EXPECT_FLOAT_EQ(result, 10.0f);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}