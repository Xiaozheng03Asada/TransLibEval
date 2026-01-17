#include <gtest/gtest.h>
#include "../src/function_numpy_sort.cpp"

TEST(SortCalculatorTest, TestValidInput)
{
    SortCalculator calc_sort;
    std::string result = calc_sort.sort<double>(10, 5, 15);
    EXPECT_NE(result.find("Sorted Values: 5"), std::string::npos);
    EXPECT_NE(result.find("10"), std::string::npos);
    EXPECT_NE(result.find("15"), std::string::npos);
}

TEST(SortCalculatorTest, TestEmptyInput)
{
    SortCalculator calc_sort;
    std::string result = calc_sort.sort<double>();
    EXPECT_NE(result.find("Sorted Values: 5"), std::string::npos);
    EXPECT_NE(result.find("10"), std::string::npos);
    EXPECT_NE(result.find("15"), std::string::npos);
}

TEST(SortCalculatorTest, TestSingleValueInput)
{
    SortCalculator calc_sort;
    std::string result = calc_sort.sort<double>(100, 100, 100);
    EXPECT_NE(result.find("Sorted Values: 100"), std::string::npos);
}

TEST(SortCalculatorTest, TestLargeNumbers)
{
    SortCalculator calc_sort;
    std::string result = calc_sort.sort<double>(1e10, 1e12, 1e11);
    EXPECT_NE(result.find("10000000000.0"), std::string::npos);
    EXPECT_NE(result.find("100000000000.0"), std::string::npos);
    EXPECT_NE(result.find("1000000000000.0"), std::string::npos);
}

TEST(SortCalculatorTest, TestMixedSignValues)
{
    SortCalculator calc_sort;
    std::string result = calc_sort.sort<double>(-10, 0, 5);
    EXPECT_NE(result.find("Sorted Values: -10"), std::string::npos);
    EXPECT_NE(result.find("0"), std::string::npos);
    EXPECT_NE(result.find("5"), std::string::npos);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}