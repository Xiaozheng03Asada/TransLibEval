#include <gtest/gtest.h>
#include "../src/function_Cerberus_Validator_validate.cpp"

class TestNumberComparer : public ::testing::Test {
protected:
    void SetUp() override {
        comparer = new NumberComparer();
    }
    void TearDown() override {
        delete comparer;
    }
    NumberComparer* comparer;
};

TEST_F(TestNumberComparer, GreaterNumbers) {
    EXPECT_EQ(comparer->compare_numbers(10, 5), "Greater");
}

TEST_F(TestNumberComparer, SmallerNumbers) {
    EXPECT_EQ(comparer->compare_numbers(5, 10), "Smaller");
}

TEST_F(TestNumberComparer, EqualNumbers) {
    EXPECT_EQ(comparer->compare_numbers(10, 10), "Equal");
}

TEST_F(TestNumberComparer, NonNumericInput) {
    // 第一个参数非数字时返回错误信息
    EXPECT_EQ(comparer->compare_numbers(std::string("a"), 5),
              "Error: Invalid input. {'num1': ['must be of number type']}");
}

TEST_F(TestNumberComparer, NonNumericInput2) {
    // 第二个参数非数字时返回错误信息
    EXPECT_EQ(comparer->compare_numbers(10, std::string("b")),
              "Error: Invalid input. {'num2': ['must be of number type']}");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}