#include <gtest/gtest.h>
#include "../src/function_numpy_reshape.cpp"

TEST(ReshapeCalculatorTest, TestValidInput)
{
    ReshapeCalculator calc;
    std::string result = calc.reshape<double>(10, 5);
    EXPECT_NE(result.find("Price: 10"), std::string::npos);
    EXPECT_NE(result.find("Quantity: 5"), std::string::npos);
    EXPECT_NE(result.find("Total Amount: 50"), std::string::npos);
}

TEST(ReshapeCalculatorTest, TestZeroValue)
{
    ReshapeCalculator calc;
    std::string result = calc.reshape<double>(0, 5);
    EXPECT_NE(result.find("Price: 0"), std::string::npos);
    EXPECT_NE(result.find("Quantity: 5"), std::string::npos);
    EXPECT_NE(result.find("Total Amount: 0"), std::string::npos);
}

TEST(ReshapeCalculatorTest, TestNegativeValue)
{
    ReshapeCalculator calc;
    std::string result = calc.reshape<double>(-10, 5);
    EXPECT_NE(result.find("Price: -10"), std::string::npos);
    EXPECT_NE(result.find("Quantity: 5"), std::string::npos);
    EXPECT_NE(result.find("Total Amount: -50"), std::string::npos);
}

TEST(ReshapeCalculatorTest, TestFloatValue)
{
    ReshapeCalculator calc;
    std::string result = calc.reshape<double>(10.5, 3.2);
    EXPECT_NE(result.find("Price: 10.5"), std::string::npos);
    EXPECT_NE(result.find("Quantity: 3.2"), std::string::npos);
    EXPECT_NE(result.find("Total Amount: 33.6"), std::string::npos);
}

TEST(ReshapeCalculatorTest, TestDefaultValues)
{
    ReshapeCalculator calc;
    std::string result = calc.reshape<double>();
    EXPECT_NE(result.find("Price: 10"), std::string::npos);
    EXPECT_NE(result.find("Quantity: 5"), std::string::npos);
    EXPECT_NE(result.find("Total Amount: 50"), std::string::npos);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}