#include <gtest/gtest.h>
#include "../src/function_sklearn_mean_squared_error.cpp"

class TestMeanSquaredErrorCalculator : public ::testing::Test {
protected:
    MeanSquaredErrorCalculator calculator;
};

TEST_F(TestMeanSquaredErrorCalculator, TestPerfectFit) {
    std::string X = "1,2,3";
    std::string y = "2,4,6";
    std::string result = calculator.calculate(X, y);
    EXPECT_NEAR(std::stod(result), 0.0, 1e-6);
}

TEST_F(TestMeanSquaredErrorCalculator, TestNonLinearData) {
    std::string X = "1,2,3,4";
    std::string y = "1,4,9,16";
    std::string result = calculator.calculate(X, y);
    EXPECT_GT(std::stod(result), 0.0);
}

TEST_F(TestMeanSquaredErrorCalculator, TestRandomDataShapeCheck) {
    std::string X = "0.1,0.2,0.3,0.4,0.5";
    std::string y = "0.2,0.4,0.6,0.8,1.1";
    std::string result = calculator.calculate(X, y);
    EXPECT_GT(std::stod(result), 0.0);
}

TEST_F(TestMeanSquaredErrorCalculator, TestRandomDataValue) {
    std::string X = "0.2,0.4,0.6,0.8,1.0";
    std::string y = "0.1,0.3,0.5,0.7,0.9";
    std::string result = calculator.calculate(X, y);
    EXPECT_GE(std::stod(result), 0.0);
}

TEST_F(TestMeanSquaredErrorCalculator, TestIncompatibleShapes) {
    std::string X = "1,2";
    std::string y = "1,2,3";
    EXPECT_THROW(calculator.calculate(X, y), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}