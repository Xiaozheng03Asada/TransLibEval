#include <gtest/gtest.h>
#include "../src/function_marshmallow_post_dump.cpp"  

TEST(TestSerializeUserData, ValidUserData) {
    UserSchema schema;
    std::string user_data = R"({"age": 30, "score": 85.0})";
    std::string result = schema.serialize_user_data(user_data);
    EXPECT_NE(result.find("\"result\":"), std::string::npos);
    EXPECT_NE(result.find("\"age\":30"), std::string::npos);
    EXPECT_NE(result.find("\"score\":"), std::string::npos);
}

TEST(TestSerializeUserData, OutputStructure) {
    UserSchema schema;
    std::string user_data = R"({"age": 50, "score": 95.5})";
    std::string result = schema.serialize_user_data(user_data);
    EXPECT_NE(result.find("\"result\":"), std::string::npos);
}

TEST(TestSerializeUserData, ExtraFieldsInInput) {
    UserSchema schema;
    std::string user_data = R"({"age": 35, "score": 88.5, "extra": "field"})";
    std::string result = schema.serialize_user_data(user_data);
    EXPECT_NE(result.find("\"result\":"), std::string::npos);
    EXPECT_NE(result.find("\"age\":35"), std::string::npos);
    EXPECT_NE(result.find("\"score\":88.5"), std::string::npos);
    EXPECT_EQ(result.find("\"extra\":"), std::string::npos);
}

TEST(TestSerializeUserData, ZeroValues) {
    UserSchema schema;
    std::string user_data = R"({"age": 0, "score": 0.0})";
    std::string result = schema.serialize_user_data(user_data);
    EXPECT_NE(result.find("\"age\":0"), std::string::npos);
    EXPECT_NE(result.find("\"score\":0"), std::string::npos);
}

TEST(TestSerializeUserData, NegativeNumbers) {
    UserSchema schema;
    std::string user_data = R"({"age": -25, "score": -5.5})";
    std::string result = schema.serialize_user_data(user_data);
    EXPECT_NE(result.find("\"age\":-25"), std::string::npos);
    EXPECT_NE(result.find("\"score\":-5.5"), std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}