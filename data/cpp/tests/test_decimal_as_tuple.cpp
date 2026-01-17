#include <gtest/gtest.h>
#include "../src/function_decimal_as_tuple.cpp"

class TestGetDecimalTuple : public ::testing::Test {
protected:
    GetDecimalTuple get_decimal_tuple_func;
};

TEST_F(TestGetDecimalTuple, TestPositiveInteger) {
    std::string result = get_decimal_tuple_func.check_discount_for_large_order("12345");
    EXPECT_EQ(result, "0,12345,0");
}

TEST_F(TestGetDecimalTuple, TestNegativeInteger) {
    std::string result = get_decimal_tuple_func.check_discount_for_large_order("-12345");
    EXPECT_EQ(result, "1,12345,0");
}

TEST_F(TestGetDecimalTuple, TestPositiveDecimal) {
    std::string result = get_decimal_tuple_func.check_discount_for_large_order("123.45");
    EXPECT_EQ(result, "0,12345,-2");
}

TEST_F(TestGetDecimalTuple, TestNegativeDecimal) {
    std::string result = get_decimal_tuple_func.check_discount_for_large_order("-123.45");
    EXPECT_EQ(result, "1,12345,-2");
}

TEST_F(TestGetDecimalTuple, TestZeroValue) {
    std::string result = get_decimal_tuple_func.check_discount_for_large_order("0");
    EXPECT_EQ(result, "0,0,0");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}