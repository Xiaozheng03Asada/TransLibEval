#include <gtest/gtest.h>
#include "../src/function_cvxpy_sum_squares.cpp"


class TestCVXPYSumOfSquaresFunction : public ::testing::Test {
protected:
    CVXPYSumOfSquaresFunction func;
};

TEST_F(TestCVXPYSumOfSquaresFunction, SumOfSquaresComputation) {
    std::string result = func.solve_sum_of_squares("1,-2,3");
    double expected_value = 1*1 + (-2)*(-2) + 3*3;
    EXPECT_NEAR(std::stod(result), expected_value, 1e-10);
}

TEST_F(TestCVXPYSumOfSquaresFunction, FloatingPointPrecision) {
    std::string result = func.solve_sum_of_squares("1.0000000001,2.0000000002,3.0000000003");
    double v1 = 1.0000000001;
    double v2 = 2.0000000002;
    double v3 = 3.0000000003;
    double expected_value = v1*v1 + v2*v2 + v3*v3;
    EXPECT_NEAR(std::stod(result), expected_value, 1e-10);
}

TEST_F(TestCVXPYSumOfSquaresFunction, SingleElementVector) {
    std::string result = func.solve_sum_of_squares("5");
    EXPECT_EQ(result, "25.0");
}

TEST_F(TestCVXPYSumOfSquaresFunction, SmallValuesInVector) {
    std::string result = func.solve_sum_of_squares("1e-6,2e-6,3e-6");
    double v1 = 1e-6;
    double v2 = 2e-6;
    double v3 = 3e-6;
    double expected_value = v1*v1 + v2*v2 + v3*v3;
    EXPECT_NEAR(std::stod(result), expected_value, 1e-10);
}

TEST_F(TestCVXPYSumOfSquaresFunction, NegativeInput) {
    std::string result = func.solve_sum_of_squares("-1,-4,-2");
    double expected_value = 1 + 16 + 4;
    EXPECT_NEAR(std::stod(result), expected_value, 1e-10);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}