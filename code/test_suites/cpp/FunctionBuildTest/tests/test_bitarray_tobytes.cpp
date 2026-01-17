#include <gtest/gtest.h>
#include "../src/function_bitarray_tobytes.cpp"

class TestConvertToBytesFunction : public ::testing::Test {
protected:
    ConvertToBytesFunction convert_to_bytes_func;
};

TEST_F(TestConvertToBytesFunction, TestConvertStandardBitarray) {
    std::string result = convert_to_bytes_func.convert_to_bytes("110101");
    EXPECT_EQ(result, "b'\\xd4'");
}

TEST_F(TestConvertToBytesFunction, TestConvertEmptyBitarray) {
    std::string result = convert_to_bytes_func.convert_to_bytes("");
    EXPECT_EQ(result, "b''");
}

TEST_F(TestConvertToBytesFunction, TestConvertPaddedBitarray) {
    std::string result = convert_to_bytes_func.convert_to_bytes("1101");
    EXPECT_EQ(result, "b'\\xd0'");
}

TEST_F(TestConvertToBytesFunction, TestConvertBitarrayWithTrailingZeros) {
    std::string result = convert_to_bytes_func.convert_to_bytes("1101000");
    EXPECT_EQ(result, "b'\\xd0'");
}

TEST_F(TestConvertToBytesFunction, TestNonBitarrayInput) {
    EXPECT_THROW(convert_to_bytes_func.convert_to_bytes("10102"), std::invalid_argument);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}