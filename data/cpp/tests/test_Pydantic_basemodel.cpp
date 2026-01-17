#include <gtest/gtest.h>
#include "../src/function_Pydantic_basemodel.cpp"

class TestCreateUser : public ::testing::Test {
protected:
    UserValidator validator;
};

TEST_F(TestCreateUser, TestValidUser) {
    std::string result = validator.create_user("John Doe", 30, "john@example.com");
    EXPECT_EQ(result, "name='John Doe' age=30 email='john@example.com'");
}

TEST_F(TestCreateUser, TestInvalidAge) {
    std::string result = validator.create_user("John Doe", -1, "john@example.com");
    EXPECT_NE(result.find("age"), std::string::npos);
}

TEST_F(TestCreateUser, TestInvalidEmail) {
    std::string result = validator.create_user("John Doe", 30, "invalid-email");
    EXPECT_NE(result.find("email"), std::string::npos);
}

TEST_F(TestCreateUser, TestMissingField) {
    std::string result = validator.create_user("John Doe", 30, "");
    EXPECT_NE(result.find("email"), std::string::npos);
}

TEST_F(TestCreateUser, TestInvalidEmailType) {
    std::string result = validator.create_user("John Doe", 30, "12345");
    EXPECT_NE(result.find("email"), std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}