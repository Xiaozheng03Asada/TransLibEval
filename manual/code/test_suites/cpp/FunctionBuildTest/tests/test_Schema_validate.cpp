#include <gtest/gtest.h>
#include "../src/function_Schema_validate.cpp"

class TestPersonValidator : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new PersonValidator();
    }

    void TearDown() override {
        delete validator;
    }

    PersonValidator* validator;
};

TEST_F(TestPersonValidator, ValidData) {
    EXPECT_EQ(validator->validate("Alice", 30, "New York"),
              "Valid data: name = Alice, age = 30, city = New York");
}

TEST_F(TestPersonValidator, InvalidAge) {
    EXPECT_EQ(validator->validate("Alice", 15, "New York"),
              "Invalid data: One or more fields are incorrect");
}

TEST_F(TestPersonValidator, InvalidCity) {
    EXPECT_EQ(validator->validate("Bob", "25", 12345),
              "Invalid data: One or more fields are incorrect");
}

TEST_F(TestPersonValidator, MissingCity) {
    EXPECT_EQ(validator->validate("Charlie", "28", ""),
              "Invalid data: One or more fields are incorrect");
}

TEST_F(TestPersonValidator, InvalidName) {
    EXPECT_EQ(validator->validate("", "45", "London"),
              "Invalid data: One or more fields are incorrect");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}