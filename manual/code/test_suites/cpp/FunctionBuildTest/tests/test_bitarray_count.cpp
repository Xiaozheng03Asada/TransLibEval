#include <gtest/gtest.h>
#include "../src/function_bitarray_count.cpp"

class TestCountBitsFunction : public ::testing::Test {
protected:
    CountBitsFunction count_bits_func;
};

TEST_F(TestCountBitsFunction, TestCountBitsRandomPattern) {
    std::string ba = "1010101111001101";
    EXPECT_EQ(count_bits_func.count_bits(ba, 1), 10);
    EXPECT_EQ(count_bits_func.count_bits(ba, 0), 6);
}

TEST_F(TestCountBitsFunction, TestCountAll) {
    std::string ba = "1111111";
    EXPECT_EQ(count_bits_func.count_bits(ba, 1), 7);

    ba = "0000000";
    EXPECT_EQ(count_bits_func.count_bits(ba, 0), 7);
}

TEST_F(TestCountBitsFunction, TestInvalidValue) {
    std::string ba = "1100";
    EXPECT_THROW(count_bits_func.count_bits(ba, 2), std::invalid_argument);
}

TEST_F(TestCountBitsFunction, TestNonStringInput) {
    EXPECT_THROW(count_bits_func.count_bits("1100", 2), std::invalid_argument);
}

TEST_F(TestCountBitsFunction, TestCountBitsSubrange) {
    std::string ba = std::string(1000, '1') + std::string(1000, '0');
    std::string sub_ba = ba.substr(500, 1000);
    EXPECT_EQ(count_bits_func.count_bits(sub_ba, 1), 500);
    EXPECT_EQ(count_bits_func.count_bits(sub_ba, 0), 500);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}