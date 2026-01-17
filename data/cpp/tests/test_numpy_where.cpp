#include <gtest/gtest.h>
#include "../src/function_numpy_where.cpp"

TEST(NumpyExampleTest, test_check_number_positive)
{
    NumpyExample calc;
    std::string result = calc.check_number<int>(5);
    EXPECT_EQ(result, "positive");
}

TEST(NumpyExampleTest, test_check_number_negative)
{
    NumpyExample calc;
    std::string result = calc.check_number<int>(-3);
    EXPECT_EQ(result, "negative");
}

TEST(NumpyExampleTest, test_check_number_zero)
{
    NumpyExample calc;
    std::string result = calc.check_number<int>(0);
    EXPECT_EQ(result, "zero");
}

TEST(NumpyExampleTest, test_check_number_large_positive)
{
    NumpyExample calc;
    std::string result = calc.check_number<int>(100);
    EXPECT_EQ(result, "positive");
}

TEST(NumpyExampleTest, test_check_number_large_negative)
{
    NumpyExample calc;
    std::string result = calc.check_number<int>(-100);
    EXPECT_EQ(result, "negative");
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}