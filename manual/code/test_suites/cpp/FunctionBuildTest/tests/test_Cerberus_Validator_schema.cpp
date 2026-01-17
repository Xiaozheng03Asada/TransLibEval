#include <gtest/gtest.h>
#include "../src/function_Cerberus_Validator_schema.cpp"

class TestDataValidator : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new DataValidator();
    }
    void TearDown() override {
        delete validator;
    }
    DataValidator* validator;
};

TEST_F(TestDataValidator, ValidData) {
    std::string data_str = "{'name': 'John', 'age': 25}";
    EXPECT_EQ(validator->validate_data(data_str),
              "Valid data: {'name': {'type': 'string', 'minlength': 3}, 'age': {'type': 'integer', 'min': 18, 'max': 100}}");
}

TEST_F(TestDataValidator, InvalidNameLength) {
    std::string data_str = "{'name': 'Jo', 'age': 25}";
    EXPECT_EQ(validator->validate_data(data_str),
              "Error: Invalid data - {'name': ['min length is 3']}");
}

TEST_F(TestDataValidator, InvalidAge) {
    std::string data_str = "{'name': 'John', 'age': 17}";
    EXPECT_EQ(validator->validate_data(data_str),
              "Error: Invalid data - {'age': ['min value is 18']}");
}

TEST_F(TestDataValidator, NonStringInput) {
    std::string data_str = "{'name': 123, 'age': 25}";
    EXPECT_EQ(validator->validate_data(data_str),
              "Error: Invalid data - {'name': ['must be of string type']}");
}

TEST_F(TestDataValidator, InvalidFormat) {
    std::string data_str = "{'name': 'Alice', 'age': 'invalid_age'}";
    EXPECT_EQ(validator->validate_data(data_str),
              "Error: Invalid data - {'age': ['must be of integer type']}");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}