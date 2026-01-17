#include <gtest/gtest.h>
#include "../src/function_Schema_Tuple.cpp"

class TestValidateTuple : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new ValidateTuple();
    }

    void TearDown() override {
        delete validator;
    }

    ValidateTuple* validator;
};

TEST_F(TestValidateTuple, ValidTuple) {
    EXPECT_EQ(validator->validate("42, hello"), "Valid tuple: (42, hello)");
}

TEST_F(TestValidateTuple, InvalidSecondElement) {
    EXPECT_EQ(validator->validate("42, 100"), "Valid tuple: (42, 100)");
}

TEST_F(TestValidateTuple, ValidOtherTuple) {
    EXPECT_EQ(validator->validate("100, world"), "Valid tuple: (100, world)");
}

TEST_F(TestValidateTuple, InvalidFirstElement) {
    EXPECT_EQ(validator->validate("hello, 42"), "Invalid input: First value must be an integer");
}

TEST_F(TestValidateTuple, NotAPair) {
    EXPECT_EQ(validator->validate("42"), "Invalid input: Must be a pair of values");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}