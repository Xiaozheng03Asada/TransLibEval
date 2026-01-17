#include <gtest/gtest.h>
#include "../src/function_cvxpy_norm.cpp"

class TestCVXPYNormFunction : public ::testing::Test {
protected:
    CVXPYNormFunction func;
};

TEST_F(TestCVXPYNormFunction, NormTypeTest) {
    std::string result = func.compute_norm("[1, 2, 3]");
    EXPECT_NO_THROW(std::stod(result));
}

TEST_F(TestCVXPYNormFunction, NormComputation2Norm) {
    std::string result = func.compute_norm("[3, 4, 0]", 2);
    EXPECT_EQ(result, "5.0");
}

TEST_F(TestCVXPYNormFunction, NormComputation1Norm) {
    std::string result = func.compute_norm("[1, -2, 3]", 1);
    EXPECT_EQ(result, "6.0");
}

TEST_F(TestCVXPYNormFunction, NormComputationInfNorm) {
    std::string result = func.compute_norm("[-1, 2, -3, 4]", std::numeric_limits<int>::max());
    EXPECT_EQ(result, "4.0");
}

TEST_F(TestCVXPYNormFunction, NormZeroVector) {
    std::string result = func.compute_norm("[0, 0, 0, 0, 0]", 2);
    EXPECT_EQ(result, "0.0");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}