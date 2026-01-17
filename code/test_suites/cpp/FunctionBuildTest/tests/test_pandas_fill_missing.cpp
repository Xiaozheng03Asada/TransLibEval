#include <gtest/gtest.h>
#include "../src/function_pandas_fill_missing.cpp"

TEST(TestFillMissingValues, test_success)
{
    FillMissingValues fill_missing;
    // 对应 Python: fill_missing_values(1, None)
    std::string result = fill_missing.fill_missing_values<int>(1, std::nullopt);
    // 测试字符串是否以 "A: 1" 开头
    ASSERT_EQ(result.rfind("A: 1", 0), 0);
}

TEST(TestFillMissingValues, test_filled_values)
{
    FillMissingValues fill_missing;
    // 对应 Python: fill_missing_values(None, None) => "A: nan, B: nan"
    std::string result = fill_missing.fill_missing_values<double>(std::nullopt, std::nullopt);
    ASSERT_EQ(result, "A: nan, B: nan");
}

TEST(TestFillMissingValues, test_result_format)
{
    FillMissingValues fill_missing;
    // Python: fill_missing_values(5, 20)
    std::string result = fill_missing.fill_missing_values<int>(5, 20);
    // 简单检验结果非空
    ASSERT_FALSE(result.empty());
}

TEST(TestFillMissingValues, test_column_names)
{
    FillMissingValues fill_missing;
    // Python: fill_missing_values(7, 14)
    std::string result = fill_missing.fill_missing_values<int>(7, 14);
    // 检查字符串中是否包含 "A" 或 "B"
    ASSERT_NE(result.find("A"), std::string::npos);
    ASSERT_NE(result.find("B"), std::string::npos);
}

TEST(TestFillMissingValues, test_partial_filled_values)
{
    FillMissingValues fill_missing;
    // Python: fill_missing_values(None, 50) => "A: nan, B: 50"
    std::string result = fill_missing.fill_missing_values<double>(std::nullopt, 50.0);
    ASSERT_EQ(result, "A: nan, B: 50");
}

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}