#include <gtest/gtest.h>
#include "../src/function_Schema_And.cpp"

class TestStringValidator : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new StringValidator();
    }

    void TearDown() override {
        delete validator;
    }

    StringValidator* validator;
};

TEST_F(TestStringValidator, ValidData) {
    EXPECT_EQ(validator->validate_user_input("admin", "securepassword123"),
              "Validation passed: username = admin, password = securepassword123");
}

TEST_F(TestStringValidator, MissingUsername) {
    EXPECT_EQ(validator->validate_user_input("", "securepassword123"),
              "Validation failed");
}

TEST_F(TestStringValidator, ShortPassword) {
    EXPECT_EQ(validator->validate_user_input("admin", "short"),
              "Validation failed");
}

TEST_F(TestStringValidator, NonStringUsername) {
    EXPECT_EQ(validator->validate_user_input(12345, "securepassword123"),
              "Validation failed");
}

TEST_F(TestStringValidator, ValidDataNoErrors) {
    EXPECT_EQ(validator->validate_user_input("admin", "securepass123"),
              "Validation passed: username = admin, password = securepass123");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}