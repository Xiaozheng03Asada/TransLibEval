#include <gtest/gtest.h>
#include "../src/function_Schema_is_valid.cpp"

class TestUserValidator : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new UserValidator();
    }

    void TearDown() override {
        delete validator;
    }

    UserValidator* validator;
};

TEST_F(TestUserValidator, ValidData) {
    EXPECT_EQ(validator->validate_user("Alice", "25", "alice@example.com"), "Valid data");
}

TEST_F(TestUserValidator, MissingOptionalField) {
    EXPECT_EQ(validator->validate_user("Bob", "30"), "Valid data");
}

TEST_F(TestUserValidator, InvalidEmailFormat) {
    EXPECT_EQ(validator->validate_user("Diana", "22", "invalid_email"), "Invalid data");
}

TEST_F(TestUserValidator, AdditionalUnexpectedField) {
    EXPECT_EQ(validator->validate_user("Eve", "30", "extra_field"), "Invalid data");
}

TEST_F(TestUserValidator, NullEmail) {
    EXPECT_EQ(validator->validate_user("Frank", "40", nullptr), "Valid data");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}