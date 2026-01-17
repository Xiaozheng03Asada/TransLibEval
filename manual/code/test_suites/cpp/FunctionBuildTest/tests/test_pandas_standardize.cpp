#include <gtest/gtest.h>
#include "../src/function_pandas_standardize.cpp"

TEST(TestStandardizeData, test_success)
{
    StandardizeData calc_standardize;
    std::string result = calc_standardize.standardize<int>(1, 2);
    // 检查结果是否以 "A:" 开头
    ASSERT_EQ(result.find("A:"), 0);
}

TEST(TestStandardizeData, test_result_format)
{
    StandardizeData calc_standardize;
    std::string result = calc_standardize.standardize<int>(1, 2);
    // 检查返回结果为非空字符串
    ASSERT_FALSE(result.empty());
}

TEST(TestStandardizeData, test_empty_data)
{
    StandardizeData calc_standardize;
    std::string result = calc_standardize.standardize<int>(std::nullopt, std::nullopt);
    // 检查字符串中包含 "A: None" 和 "B: None"
    ASSERT_NE(result.find("A: None"), std::string::npos);
    ASSERT_NE(result.find("B: None"), std::string::npos);
}

TEST(TestStandardizeData, test_column_consistency)
{
    StandardizeData calc_standardize;
    std::string result = calc_standardize.standardize<int>(1, 2);
    // 检查结果包含 "A:" 和 "B:"
    ASSERT_NE(result.find("A:"), std::string::npos);
    ASSERT_NE(result.find("B:"), std::string::npos);
}

TEST(TestStandardizeData, test_same_value_input)
{
    StandardizeData calc_standardize;
    std::string result = calc_standardize.standardize<int>(5, 5);
    // 同一值输入，标准化结果应为0
    ASSERT_NE(result.find("A: 0"), std::string::npos);
    ASSERT_NE(result.find("B: 0"), std::string::npos);
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}