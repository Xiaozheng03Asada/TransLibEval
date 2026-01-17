#include <gtest/gtest.h>
#include "../src/function_jsonschema_descend.cpp"

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
    EXPECT_EQ(validator.validate_json(data, schema), "Validation failed");
}

TEST_F(TestJSONValidator, TestCase3) {
    std::string data = R"({"name": "John", "age": "30"})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "Validation failed");
}

TEST_F(TestJSONValidator, TestCase4) {
    std::string data = R"({"name": "John"})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}}, "required": ["name"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "JSON is valid");
}

TEST_F(TestJSONValidator, TestCase5) {
    std::string data = R"({"name": "John", "address": {"street": "123 Main St", "city": "New York"}})";
    std::string schema = R"({"type": "object", "properties": {"name": {"type": "string"}, "address": {"type": "object", "properties": {"street": {"type": "string"}, "city": {"type": "string"}}, "required": ["street", "city"]}}, "required": ["name", "address"]})";
    EXPECT_EQ(validator.validate_json(data, schema), "JSON is valid");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}