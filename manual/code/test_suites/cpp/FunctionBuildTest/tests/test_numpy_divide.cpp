#include <gtest/gtest.h>
#include "../src/function_numpy_divide.cpp"

TEST(TestDivideExample, test_divide_same_shape_1d_arrays)
{
    DivisionCalculator division_calculator;
    double result = division_calculator.divide<double>(4, 2);
    double expected_result = 2.0;
    EXPECT_DOUBLE_EQ(result, expected_result);
}

TEST(TestDivideExample, test_divide_same_shape_2d_arrays)
{
    DivisionCalculator division_calculator;
    double result = division_calculator.divide<double>(4, 2);
    double expected_result = 2.0;
    EXPECT_DOUBLE_EQ(result, expected_result);
}

TEST(TestDivideExample, test_divide_1d_array_by_scalar)
{
    DivisionCalculator division_calculator;
    double result = division_calculator.divide<double>(6, std::nullopt, 2);
    double expected_result = 3.0;
    EXPECT_DOUBLE_EQ(result, expected_result);
}

TEST(TestDivideExample, test_divide_1d_array_with_zero_by_non_zero_scalar)
{
    DivisionCalculator division_calculator;
    double result = division_calculator.divide<double>(0, std::nullopt, 2);
    double expected_result = 0.0;
    EXPECT_DOUBLE_EQ(result, expected_result);
}

TEST(TestDivideExample, test_divide_broadcastable_arrays)
{
    DivisionCalculator division_calculator;
    double result = division_calculator.divide<double>(6, 2);
    double expected_result = 3.0;
    EXPECT_DOUBLE_EQ(result, expected_result);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}