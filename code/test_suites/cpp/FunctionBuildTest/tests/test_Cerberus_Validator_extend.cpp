#include <gtest/gtest.h>
#include "../src/function_Cerberus_Validator_extend.cpp"

class TestSimpleValidator : public ::testing::Test {
protected:
    void SetUp() override {
        schema_str = R"({"age": {"type": "integer", "min": 18, "max": 100}, "name": {"type": "string", "maxlength": 50}})";
        validator = new SimpleValidator();
    }

    void TearDown() override {
        delete validator;
    }

    std::string schema_str;
    SimpleValidator* validator;
};

TEST_F(TestSimpleValidator, ValidData) {
    std::string data_str = R"({"age": 25, "name": "John Doe"})";
    EXPECT_EQ(validator->validate_data(data_str, schema_str), "Valid data");
}

TEST_F(TestSimpleValidator, InvalidAge) {
    std::string data_str = R"({"age": 17, "name": "John Doe"})";
    EXPECT_EQ(validator->validate_data(data_str, schema_str), R"(Invalid data: {"age": ["min value is 18"]})");
}

TEST_F(TestSimpleValidator, InvalidNameLength) {
    std::string data_str = R"({"age": 30, "name": ")" + std::string(51, 'a') + R"("})";
    EXPECT_EQ(validator->validate_data(data_str, schema_str), R"(Invalid data: {"name": ["max length is 50"]})");
}

TEST_F(TestSimpleValidator, InvalidNameType) {
    std::string data_str = R"({"age": 25, "name": 12345})";
    EXPECT_EQ(validator->validate_data(data_str, schema_str), R"(Invalid data: {"name": ["must be of string type"]})");
}
TEST_F(TestSimpleValidator, MultipleErrors) {
    std::string data_str = R"({"age": 17, "name": ")" + std::string(51, 'a') + R"("})";
    std::string expected = R"(Invalid data: {"age": ["min value is 18"],"name": ["max length is 50"]})";
    EXPECT_EQ(validator->validate_data(data_str, schema_str), expected);
}
int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}