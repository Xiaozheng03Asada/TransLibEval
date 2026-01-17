
#include <gtest/gtest.h>
#include "../src/function_itertools_cycle.cpp"

class TestCycleExample : public testing::Test {
protected:
    CycleProcessor processor;
};

TEST_F(TestCycleExample, EmptySequence) {
    std::string result = processor.test_cycle("", 5);
    EXPECT_EQ(result, "");
}

TEST_F(TestCycleExample, SingleCharacter) {
    std::string result = processor.test_cycle("A", 5);
    EXPECT_EQ(result, "AAAAA");
}

TEST_F(TestCycleExample, ShortSequence) {
    std::string result = processor.test_cycle("AB", 5);
    EXPECT_EQ(result, "ABABA");
}

TEST_F(TestCycleExample, LongSequence) {
    std::string result = processor.test_cycle("ABCDEFG", 10);
    EXPECT_EQ(result, "ABCDEFGABC");
}

TEST_F(TestCycleExample, NumericSequence) {
    std::string result = processor.test_cycle("123", 7);
    EXPECT_EQ(result, "1231231");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}