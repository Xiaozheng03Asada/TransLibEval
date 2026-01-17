#include <gtest/gtest.h>
#include "../src/function_jsonschema_iter_errors.cpp"

class TestJSONValidator : public ::testing::Test {
protected:
    JSONValidator validator;
};

TEST_F(TestJSONValidator, TestCase1) {
    std::string data = R"({"name": "John", "age": 30})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "JSON is valid");
}

TEST_F(TestJSONValidator, TestCase2) {
    std::string data = R"({"name": "John"})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "Validation failed: 'age' is a required property");
}

TEST_F(TestJSONValidator, TestCase3) {
    std::string data = R"({"name": "John", "age": "30"})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "Validation failed: '30' is not of type 'integer'");
}

TEST_F(TestJSONValidator, TestCase4) {
    std::string data = R"({})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "Validation failed: 'name' is a required property");
}

TEST_F(TestJSONValidator, TestCase5) {
    std::string data = R"({"name": "John", "age": 30})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}}, "required": ["name"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "JSON is valid");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}