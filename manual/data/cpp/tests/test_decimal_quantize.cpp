#include <gtest/gtest.h>
#include "../src/function_decimal_quantize.cpp"

class TestDecimalPrecision : public ::testing::Test {
protected:
    SetDecimalPrecision set_decimal_precision_func;
};

TEST_F(TestDecimalPrecision, TestRoundToThreeDecimalPlaces) {
    std::string result = set_decimal_precision_func.check_discount_for_large_order("2.71828", 3);
    EXPECT_EQ(result, "2.718");
}

TEST_F(TestDecimalPrecision, TestRoundUp) {
    std::string result = set_decimal_precision_func.check_discount_for_large_order("1.2345", 2);
    EXPECT_EQ(result, "1.23");
}

TEST_F(TestDecimalPrecision, TestRoundDown) {
    std::string result = set_decimal_precision_func.check_discount_for_large_order("0.99999", 3);
    EXPECT_EQ(result, "1.000");
}

TEST_F(TestDecimalPrecision, TestInvalidValue) {
    std::string result = set_decimal_precision_func.check_discount_for_large_order("invalid", 2);
    EXPECT_EQ(result, "Error");
}

TEST_F(TestDecimalPrecision, TestNegativeNumber) {
    std::string result = set_decimal_precision_func.check_discount_for_large_order("-5.6789", 2);
    EXPECT_EQ(result, "-5.68");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}