#include <gtest/gtest.h>
#include "../src/function_bitarray_invert.cpp"


class TestInvertBitsFunction : public ::testing::Test {
protected:
    InvertBitsFunction invert_bits_func;
};

TEST_F(TestInvertBitsFunction, TestInvertAllBits) {
    std::string result = invert_bits_func.invert_bitarray("101010");
    EXPECT_EQ(result, "010101");
}

TEST_F(TestInvertBitsFunction, TestInvertPartialBits) {
    std::string result = invert_bits_func.invert_bitarray("111000", "1", "4");
    EXPECT_EQ(result, "100100");
}

TEST_F(TestInvertBitsFunction, TestInvertSecondHalf) {
    std::string result = invert_bits_func.invert_bitarray("110011", "3", "6");
    EXPECT_EQ(result, "110100");
}

TEST_F(TestInvertBitsFunction, TestInvertLargeAlternatingPattern) {
    std::string bits;
    for (int i = 0; i < 5000; ++i) {
        bits += "01";
    }
    std::string expected;
    for (int i = 0; i < 5000; ++i) {
        expected += "10";
    }
    std::string result = invert_bits_func.invert_bitarray(bits);
    EXPECT_EQ(result, expected);
}

TEST_F(TestInvertBitsFunction, TestInvalidIndices) {
    EXPECT_THROW(invert_bits_func.invert_bitarray("101010", "1.5", "4"), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}