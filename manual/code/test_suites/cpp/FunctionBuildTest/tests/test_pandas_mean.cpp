#include <gtest/gtest.h>

#include "../src/function_pandas_mean.cpp"

TEST(TestCalculateMean, test_success)
{
    CalculateMean calc;
    std::string result = calc.calculate_mean<int>(1, 2);
    // 检查字符串是否以 "A:" 开头
    ASSERT_EQ(result.find("A:"), 0);
}

TEST(TestCalculateMean, test_mean_values)
{
    CalculateMean calc;
    std::string result = calc.calculate_mean<int>(1, 2);
    // 结果格式 "A: 1.0, B: 2.0"，解析均值
    size_t pos_comma = result.find(", ");
    ASSERT_NE(pos_comma, std::string::npos);

    std::string a_str = result.substr(0, pos_comma); // "A: 1.0"
    std::string b_str = result.substr(pos_comma + 2);  // "B: 2.0"
    size_t pos_colon_a = a_str.find(":");
    size_t pos_colon_b = b_str.find(":");
    ASSERT_NE(pos_colon_a, std::string::npos);
    ASSERT_NE(pos_colon_b, std::string::npos);

    double mean_A = std::stod(a_str.substr(pos_colon_a + 1));
    double mean_B = std::stod(b_str.substr(pos_colon_b + 1));

    EXPECT_NEAR(mean_A, 1.0, 0.1);
    EXPECT_NEAR(mean_B, 2.0, 0.1);
}

TEST(TestCalculateMean, test_result_format)
{
    CalculateMean calc;
    std::string result = calc.calculate_mean<int>(1, 2);
    // 检查结果为非空字符串
    ASSERT_FALSE(result.empty());
}

TEST(TestCalculateMean, test_empty_data)
{
    CalculateMean calc;
    std::string result = calc.calculate_mean<int>(std::nullopt, std::nullopt);
    // 检查字符串中包含 "A: None" 和 "B: None"
    ASSERT_NE(result.find("A: None"), std::string::npos);
    ASSERT_NE(result.find("B: None"), std::string::npos);
}

TEST(TestCalculateMean, test_column_consistency)
{
    CalculateMean calc;
    std::string result = calc.calculate_mean<int>(1, 2);
    // 检查结果包含 "A:" 和 "B:"
    ASSERT_NE(result.find("A:"), std::string::npos);
    ASSERT_NE(result.find("B:"), std::string::npos);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}