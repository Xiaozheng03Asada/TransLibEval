#include <gtest/gtest.h>
#include "../src/function_pandas_pivot_table.cpp"

TEST(TestCreatePivotTable, test_success) {
    CreatePivotTable calc_pivot;
    std::string result = calc_pivot.create_pivot_table<int>();
    ASSERT_TRUE(result.find("Date:") == 0);
}

TEST(TestCreatePivotTable, test_pivot_table_structure) {
    CreatePivotTable calc_pivot;
    std::string result = calc_pivot.create_pivot_table<int>();
    ASSERT_NE(result.find("Date:"), std::string::npos);
}

TEST(TestCreatePivotTable, test_column_names) {
    CreatePivotTable calc_pivot;
    std::string result = calc_pivot.create_pivot_table<int>();
    ASSERT_NE(result.find("Category A:"), std::string::npos);
    ASSERT_NE(result.find("Category B:"), std::string::npos);
}

TEST(TestCreatePivotTable, test_result_format) {
    CreatePivotTable calc_pivot;
    std::string result = calc_pivot.create_pivot_table<int>();
    ASSERT_FALSE(result.empty());
}

TEST(TestCreatePivotTable, test_missing_value_check) {
    CreatePivotTable calc_pivot;
    std::string result = calc_pivot.create_pivot_table<int>(
        "2023-01-01", "2023-01-02", "A", "B", 5, std::nullopt);
    ASSERT_NE(result.find("Category B: 0"), std::string::npos);
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}