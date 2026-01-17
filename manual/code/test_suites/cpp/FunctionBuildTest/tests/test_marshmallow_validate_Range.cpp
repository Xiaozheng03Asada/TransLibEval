#include <gtest/gtest.h>
#include "../src/function_marshmallow_validate_Range.cpp"  

TEST(TestUserValidator, ValidData) {
    UserValidator validator;
    std::string user_data = R"({"age": 25, "score": 85.5})";
    std::string result = validator.validate_user_data(user_data);
    EXPECT_NE(result.find("\"age\":25"), std::string::npos);
    EXPECT_NE(result.find("\"score\":85.5"), std::string::npos);
}

TEST(TestUserValidator, InvalidAgeTooLowOrHigh) {
    UserValidator validator;
    std::string user_data = R"({"age": -1, "score": 85.5})";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");

    user_data = R"({"age": 150, "score": 85.5})";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");
}

TEST(TestUserValidator, InvalidScoreTooLowOrHigh) {
    UserValidator validator;
    std::string user_data = R"({"age": 25, "score": -5.0})";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");

    user_data = R"({"age": 25, "score": 105.0})";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");
}

TEST(TestUserValidator, AdditionalUnexpectedField) {
    UserValidator validator;
    std::string user_data = R"({"age": 25, "score": 85.0, "name": "John Doe"})";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");
}

TEST(TestUserValidator, BothFieldsMissing) {
    UserValidator validator;
    std::string user_data = "{}";
    EXPECT_EQ(validator.validate_user_data(user_data), "Error: invalid input");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}