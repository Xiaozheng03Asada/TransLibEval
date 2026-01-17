#include <gtest/gtest.h>
#include "../src/function_decimal_infinite.cpp"

class TestInfiniteFunction : public ::testing::Test {
protected:
    InfiniteCheck infinite_check_func;
};

TEST_F(TestInfiniteFunction, TestPositiveInfinity) {
    std::string result = infinite_check_func.check_discount_for_large_order("Infinity");
    EXPECT_EQ(result, "True");
}

TEST_F(TestInfiniteFunction, TestNegativeInfinity) {
    std::string result = infinite_check_func.check_discount_for_large_order("-Infinity");
    EXPECT_EQ(result, "True");
}

TEST_F(TestInfiniteFunction, TestZero) {
    std::string result = infinite_check_func.check_discount_for_large_order("0.0");
    EXPECT_EQ(result, "False");
}

TEST_F(TestInfiniteFunction, TestFiniteNumber) {
    std::string result = infinite_check_func.check_discount_for_large_order("10.5");
    EXPECT_EQ(result, "False");
}

TEST_F(TestInfiniteFunction, TestNaN) {
    std::string result = infinite_check_func.check_discount_for_large_order("NaN");
    EXPECT_EQ(result, "False");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}