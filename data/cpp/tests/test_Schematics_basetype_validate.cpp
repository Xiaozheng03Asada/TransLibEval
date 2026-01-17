#include <gtest/gtest.h>
#include "../src/function_Schematics_basetype_validate.cpp"

class TestPositiveIntegerType : public ::testing::Test {
protected:
    PositiveIntegerType field;
};

TEST_F(TestPositiveIntegerType, test_valid_positive_integer) {
    std::string input_value = "5";
    std::string result = field.validate(input_value);
    EXPECT_EQ(result, "Validation successful.");
}

TEST_F(TestPositiveIntegerType, test_invalid_non_integer) {
    std::string result = field.validate("not_an_integer");
    EXPECT_EQ(result, "Value must be an integer.");
}

TEST_F(TestPositiveIntegerType, test_invalid_negative_integer) {
    std::string result = field.validate("-10");
    EXPECT_EQ(result, "Value must be a positive integer.");
}

TEST_F(TestPositiveIntegerType, test_invalid_float) {
    std::string result = field.validate("10.5");
    EXPECT_EQ(result, "Value must be an integer.");
}

TEST_F(TestPositiveIntegerType, test_valid_integer_type_conversion) {
    std::string input_value = "10";  
    std::string result = field.validate(input_value);
    EXPECT_EQ(result, "Validation successful.");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}