#include <gtest/gtest.h>
#include "../src/function_decimal_adjusted.cpp"

class TestGetDecimalAdjusted : public ::testing::Test {
protected:
    GetDecimalAdjusted get_decimal_adjusted_func;
};

TEST_F(TestGetDecimalAdjusted, TestLargeValue) {
    std::string result = get_decimal_adjusted_func.check_discount_for_large_order(12345.6789);
    EXPECT_EQ(result, "4");
}

TEST_F(TestGetDecimalAdjusted, TestSmallValue) {
    std::string result = get_decimal_adjusted_func.check_discount_for_large_order(0.00012345);
    EXPECT_EQ(result, "-4");
}

TEST_F(TestGetDecimalAdjusted, TestNoDecimal) {
    std::string result = get_decimal_adjusted_func.check_discount_for_large_order(12345);
    EXPECT_EQ(result, "4");
}

TEST_F(TestGetDecimalAdjusted, TestLargeNegativeValue) {
    std::string result = get_decimal_adjusted_func.check_discount_for_large_order(-9876.54321);
    EXPECT_EQ(result, "3");
}

TEST_F(TestGetDecimalAdjusted, TestLargeNegativeRangeValue) {
    std::string result = get_decimal_adjusted_func.check_discount_for_large_order(-1e+100);
    EXPECT_EQ(result, "100");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}