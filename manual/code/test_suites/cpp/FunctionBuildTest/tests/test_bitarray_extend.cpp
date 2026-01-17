#include <gtest/gtest.h>
#include "../src/function_bitarray_extend.cpp"

class TestBitarrayExtender : public ::testing::Test {
protected:
    BitarrayExtender extender;
};

TEST_F(TestBitarrayExtender, TestExtendWithValidBits) {
    std::string result = extender.extend_bits("101", "010");
    EXPECT_EQ(result, "101010");
}

TEST_F(TestBitarrayExtender, TestInvalidInputNonBinary) {
    EXPECT_THROW(extender.extend_bits("101", "102"), std::invalid_argument);
}

TEST_F(TestBitarrayExtender, TestNonStringInput) {
    EXPECT_THROW(extender.extend_bits("101", "abc"), std::invalid_argument);
}

TEST_F(TestBitarrayExtender, TestExtendWithEmptyString) {
    std::string result = extender.extend_bits("101", "");
    EXPECT_EQ(result, "101");
}

TEST_F(TestBitarrayExtender, TestExtendEmptyStringWithBits) {
    std::string result = extender.extend_bits("", "010");
    EXPECT_EQ(result, "010");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}